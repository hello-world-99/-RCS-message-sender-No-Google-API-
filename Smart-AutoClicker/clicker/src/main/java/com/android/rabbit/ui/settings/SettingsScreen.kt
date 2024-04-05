package com.android.clicker.ui.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.clicker.rabbitmq.RabbitMQManager
import com.android.rabbit.ui.settings.saveSettingsToPreferences


@RequiresApi(Build.VERSION_CODES.KITKAT)
@Preview
@Composable
fun PreviewScreen(){
SettingsScreen(rememberNavController())
}



@RequiresApi(Build.VERSION_CODES.KITKAT)
@Composable
fun SettingsScreen(navController: NavController) {

    val hostState = remember { mutableStateOf(RabbitMQManager.connectionModel.host) }
    val portState = remember { mutableStateOf(RabbitMQManager.connectionModel.port.toString()) }
    val queueState = remember { mutableStateOf(RabbitMQManager.queue) }
    val usernameState = remember { mutableStateOf(RabbitMQManager.connectionModel.username) }
    val passwordState = remember { mutableStateOf(RabbitMQManager.connectionModel.password) }
    val secretkeyState = remember { mutableStateOf(RabbitMQManager.secretKey) }

    Scaffold(Modifier.fillMaxSize()) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .background(
                    color = Color(red = 0x22, green = 0x25, blue = 0x33, alpha = 0xFF)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Settings",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
                Column(
                    modifier = Modifier.padding(10.dp,4.dp,10.dp,4.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "HOST",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left
                    )
                    TextField(
                        value = hostState.value,
                        onValueChange = {
                            hostState.value = it
                            RabbitMQManager.connectionModel.host=it
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        label = {
                            Text(
                                text= "RABBITMQ_HOST=",
                                fontSize = 8.sp,
                                color = Color.DarkGray) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,

                            )
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp,4.dp,10.dp,4.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "PORT",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left
                    )
                    TextField(
                        value = portState.value,
                        onValueChange = {
                            portState.value = it
                            RabbitMQManager.connectionModel.port=it.toInt()
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        label = {
                            Text(
                                text= "RABBITMQ_PORT=",
                                fontSize = 8.sp,
                                color = Color.DarkGray) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,

                            )
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp,4.dp,10.dp,4.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "QUEUE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left
                    )
                    TextField(
                        value = queueState.value,
                        onValueChange = {
                            queueState.value = it
                            RabbitMQManager.queue=it
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        label = {
                            Text(
                                text= "RABBITMQ_QUEUE=",
                                fontSize = 8.sp,
                                color = Color.DarkGray) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,

                            )
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp,4.dp,10.dp,4.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "USER",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left
                    )
                    TextField(
                        value = usernameState.value,
                        onValueChange = {
                            usernameState.value= it
                            RabbitMQManager.connectionModel.username=it
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        label = {
                            Text(
                                text= "RABBITMQ_USER=",
                                fontSize = 8.sp,
                                color = Color.DarkGray) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,

                            )
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp,4.dp,10.dp,4.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "PASSWORD",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left
                    )
                    TextField(
                        value = passwordState.value,
                        onValueChange = {
                            passwordState.value = it
                            RabbitMQManager.connectionModel.password=it
                                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        label = {
                            Text(
                                text= "RABBITMQ_PASSWORD=",
                                fontSize = 8.sp,
                                color = Color.DarkGray) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,

                            )
                    )
                }
                Column(
                    modifier = Modifier.padding(10.dp,4.dp,10.dp,4.dp)
                ){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = "SECRET KEY",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Left
                    )
                    TextField(
                        value = secretkeyState.value,
                        onValueChange = {
                            secretkeyState.value = it
                            RabbitMQManager.secretKey=it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        label = {
                            Text(
                                text= "secret_key:",
                                fontSize = 8.sp,
                                color = Color.DarkGray) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.LightGray,

                            )
                    )
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp,16.dp,10.dp,10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    BackButton(navController)
                    ConfirmButton(navController)
                }

            }

        }
    }

}


@Composable
fun BackButton(navController: NavController){
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
            )
        ,

        onClick = {

            navController.navigate("home screen")
        }
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.White,
            modifier = Modifier.size(64.dp)
        )
    }

}

@RequiresApi(Build.VERSION_CODES.KITKAT)
@Composable
fun ConfirmButton(navController: NavController){
    val scope = rememberCoroutineScope()
    var context= LocalContext.current
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
            )
        ,


        onClick = {
            saveSettingsToPreferences(context)
            RabbitMQManager.inProcess =false
            RabbitMQManager.connect()
            navController.navigate("home screen")
        }
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = "Confirm",
            tint = Color.Green,
            modifier = Modifier.size(64.dp)
        )
    }
}