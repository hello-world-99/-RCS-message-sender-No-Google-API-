package com.android.rabbit.ui.settings

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.clicker.data.ConnectionModel
import com.android.clicker.rabbitmq.Listener

@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
fun saveSettingsToPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("HOST", Listener.connectionModel.host)
        putInt("PORT", Listener.connectionModel.port)
        putString("QUEUE", Listener.connectionModel.queue)
        putString("USER", Listener.connectionModel.username)
        putString("PASSWORD", Listener.connectionModel.password)
        putString("SECRETKEY", Listener.secretKey)
        apply()
    }
}