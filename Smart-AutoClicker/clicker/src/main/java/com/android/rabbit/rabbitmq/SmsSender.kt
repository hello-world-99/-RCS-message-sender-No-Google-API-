package com.android.clicker.rabbitmq

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.telephony.SmsManager
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import com.android.clicker.data.MessageModel
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@OptIn(DelicateCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.DONUT)
fun SmsSender(context: Context) {
    val gson = Gson()
    GlobalScope.launch {
        while (Listener.start){
            if (Listener.messageList.isNotEmpty())
            {
                val signalString: String =Listener.messageList.pollFirst()
                val signal = gson.fromJson(signalString,MessageModel::class.java)

                if (signal != null&&signal.phone != null) {
                    Listener.lastId=signal.sms_id
                    val smsIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:${signal.phone}"))
                    smsIntent.putExtra("sms_body", signal.text)
                    context.startActivity(smsIntent)
                    delay(5000)
                }
            }

        }
    }
}