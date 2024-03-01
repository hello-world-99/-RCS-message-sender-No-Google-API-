package com.android.clicker.ui.home

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

import com.android.clicker.data.CallbackModel
import com.android.clicker.rabbitmq.Listener
import com.android.clicker.rabbitmq.Listener.callBack

import com.android.clicker.rabbitmq.SmsSender
import kotlinx.coroutines.DelicateCoroutinesApi


@RequiresApi(Build.VERSION_CODES.R)
@Preview
@Composable
fun PreviewScreen() {
    HomeScreen(rememberNavController())
}

@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun HomeScreen(navController: NavController) {
    var context= LocalContext.current
    Scaffold(Modifier.fillMaxSize()) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    color = Color(red = 0x22, green = 0x25, blue = 0x33, alpha = 0xFF)
                )
        ) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 0x22, green = 0x25, blue = 0x33, alpha = 0xFF), contentColor = Color.White
                    ),
                    onClick = {
                        callBack(Listener.lastId.toInt(),true)

                    }
                ) {

                }
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 0x22, green = 0x25, blue = 0x33, alpha = 0xFF), contentColor = Color.White
                    ),
                    onClick = {
                        callBack(Listener.lastId.toInt(),false)

                    }
                ) {

                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {


                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Clicker",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(32.dp))


                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = if(Listener.state){
                            Listener.messageList.size.toString()
                        }
                               else{
                            "No connection to ${Listener.connectionModel.queue}"
                               },
                        color = Color.White,
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                Spacer(modifier = Modifier.padding(112.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    StartButton()
                    Spacer(modifier = Modifier.padding(4.dp))
                    SettingsButton(navController)
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
        }
    }

}

@OptIn(DelicateCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.R)
@Composable
fun StartButton() {
    val context = LocalContext.current

    Button(
        shape = RoundedCornerShape(percent = 10),
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.White.copy(0.5f),
                shape = RoundedCornerShape(percent = 10)
            )

            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(
                151,
                169,
                246,
                alpha = 0x32
            ), contentColor = Color.White
        ),
        onClick = {



            Listener.start=true
            SmsSender(context)
        }) {
        Text(
            text = "Start",
            modifier = Modifier
                .padding(
                    horizontal = 40.dp,
                    vertical = 5.dp
                ),
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SettingsButton(navController: NavController) {
    IconButton(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.White.copy(0.5f),
                shape = RoundedCornerShape(percent = 10)
            )
            .background(
                color = Color(
                    151,
                    169,
                    246,
                    alpha = 0x32
                ), shape = RoundedCornerShape(percent = 10)
            ),

        onClick = { navController.navigate("settings screen") }
    ) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = Color.White,
            modifier = Modifier.size(64.dp)
        )
    }
}
