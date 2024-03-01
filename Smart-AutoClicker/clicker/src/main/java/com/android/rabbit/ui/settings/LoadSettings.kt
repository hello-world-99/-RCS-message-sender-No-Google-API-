package com.android.rabbit.ui.settings

import android.content.Context
import com.android.clicker.data.ConnectionModel
import com.android.clicker.rabbitmq.Listener

fun loadSettingsFromPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    Listener.connectionModel.host = sharedPreferences.getString("HOST", "192.168.0.123") ?: "192.168.0.123"
    Listener.connectionModel.port = sharedPreferences.getInt("PORT", 5672)
    Listener.connectionModel.queue = sharedPreferences.getString("QUEUE", "my_test_queue") ?: "my_test_queue"
    Listener.connectionModel.username = sharedPreferences.getString("USER", "username") ?: "username"
    Listener.connectionModel.password = sharedPreferences.getString("PASSWORD", "PASSWORD") ?: "PASSWORD"
    Listener.secretKey = sharedPreferences.getString("SECRETKEY", "my_secret_key") ?: "my_secret_key"
}