package com.android.rabbit.ui.settings

import android.content.Context

import com.android.clicker.rabbitmq.RabbitMQManager

fun loadSettingsFromPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    RabbitMQManager.connectionModel.host = sharedPreferences.getString("HOST", "192.168.0.123") ?: "192.168.0.123"
    RabbitMQManager.connectionModel.port = sharedPreferences.getInt("PORT", 5672)
    RabbitMQManager.queue = sharedPreferences.getString("QUEUE", "my_test_queue") ?: "my_test_queue"
    RabbitMQManager.connectionModel.username = sharedPreferences.getString("USER", "username") ?: "username"
    RabbitMQManager.connectionModel.password = sharedPreferences.getString("PASSWORD", "PASSWORD") ?: "PASSWORD"
    RabbitMQManager.secretKey = sharedPreferences.getString("SECRETKEY", "my_secret_key") ?: "my_secret_key"
}