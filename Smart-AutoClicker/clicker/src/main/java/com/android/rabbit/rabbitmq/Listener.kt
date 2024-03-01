package com.android.clicker.rabbitmq

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.clicker.data.CallbackModel
import com.android.clicker.data.ConnectionModel
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.jsonBody
import com.google.gson.Gson

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import com.github.kittinunf.result.Result.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets
import java.util.LinkedList


object Listener {

    val connectionModel = ConnectionModel(
        "192.168.0.123",
        5672,
        "my_test_queue",
        "username",
        "PASSWORD"
    )

    private var message: String? = null
    var messageList: LinkedList<String> = LinkedList()
    var start: Boolean = false
    var state: Boolean = false
    var lastId:String="2"
    var secretKey:String="my_secret_key"
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    @OptIn(DelicateCoroutinesApi::class)
    fun listenerStart(state: Boolean) {

        GlobalScope.launch(Dispatchers.IO) {

            try {
                val factory = ConnectionFactory()

                factory.host = connectionModel.host
                factory.port = connectionModel.port
                factory.username = connectionModel.username
                factory.password = connectionModel.password

                val connection = factory.newConnection()
                val channel = connection.createChannel()
                Listener.state = true


                val queueName = connectionModel.queue
                channel.queueDeclare(queueName, true, false, false, null)
                while (state) {
                    if (messageList.isEmpty()){

                        val delivery = channel.basicGet(queueName, true)

                        if (delivery != null) {
                            val receivedMessage = delivery.body.toString(StandardCharsets.UTF_8)
                            messageList += receivedMessage
                        }
                    }



                }

            } catch (e: Exception) {
                Listener.state = false
                message = e.localizedMessage
            }
        }
    }
    fun callBack(sms_id: Int, isSuccess:Boolean) {
        GlobalScope.launch {
            val gson = Gson()



            var res= if(isSuccess){ 1 } else{ 0 }
            val callbackModel = CallbackModel(sms_id = sms_id, res = res, secret_key = Listener.secretKey)

            // Convert CallbackModel to JSON
            val jsonPayload = gson.toJson(callbackModel)
            // Add your JSON message processing logic here
            val factory = ConnectionFactory().apply {
                host = connectionModel.host
                port = connectionModel.port
                username =  connectionModel.username
                password =  connectionModel.password
            }

            val connection = factory.newConnection()
            val channel = connection.createChannel()
            channel.queueDeclare(connectionModel.queue, true, false, false, null)
            channel.basicPublish("", connectionModel.queue, null, jsonPayload.toByteArray())
            // Parse the received JSON message

            val receivedCallbackModel = gson.fromJson(jsonPayload, CallbackModel::class.java)

            // Modify the receivedCallbackModel as needed

            // Send POST request to FastAPI server
            val (request, response, result) = Fuel.post("http://${connectionModel.host}:80/api/callback_send_rcs")
                .jsonBody(gson.toJson(receivedCallbackModel))
                .header("Content-Type" to "application/json")
                .responseString()

            // Handle the response as needed
            when (result) {

                is Success ->   println("POST request successful. Response: ${result.value}")
                is Failure ->{ println("Failed to send POST request: ${result.error}")
                    // Print response body if available
                    result.error.response.body().let {
                        val responseBody = String(it.toByteArray(), Charsets.UTF_8)
                        println("Response body: $responseBody")
                    }
                }

                else -> {}
            }
        }

    }
}