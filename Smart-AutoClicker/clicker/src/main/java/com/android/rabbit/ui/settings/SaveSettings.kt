package com.android.rabbit.ui.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

import com.android.clicker.rabbitmq.RabbitMQManager

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
fun saveSettingsToPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("HOST", RabbitMQManager.connectionModel.host)
        putInt("PORT", RabbitMQManager.connectionModel.port)
        putString("QUEUE", RabbitMQManager.queue)
        putString("USER", RabbitMQManager.connectionModel.username)
        putString("PASSWORD", RabbitMQManager.connectionModel.password)
        putString("SECRETKEY", RabbitMQManager.secretKey)
        apply()
    }
}