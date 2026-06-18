package com.ar.stopwatchapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ar.stopwatchapp.ui.theme.StopwatchAppTheme
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            StopwatchAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    StopwatchScreen()
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun StopwatchScreen() {
    var elapsedTime by remember { mutableLongStateOf(0L) }
    var startTime by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(false) }
    val hours = elapsedTime / 3600000
    val minutes = (elapsedTime % 3600000) / 60000
    val seconds = (elapsedTime % 60000) / 1000
    val centiseconds = (elapsedTime % 1000) / 10
    LaunchedEffect(isRunning) {
        while (isRunning) {
            elapsedTime = android.os.SystemClock.elapsedRealtime() - startTime
            delay(10)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "STOPWATCH", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = if (isRunning) "Running" else "Paused")
        Spacer(modifier = Modifier.height(24.dp))
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) { Text(text = String.format("%02d:%02d:%02d.%02d", hours, minutes, seconds, centiseconds), modifier = Modifier.padding(24.dp), fontSize = 48.sp, fontFamily = FontFamily.Monospace) }
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Button(modifier = Modifier.height(80.dp).width(120.dp), onClick = { if(!isRunning) { startTime = android.os.SystemClock.elapsedRealtime() - elapsedTime }; isRunning = !isRunning }) { Text(text = if (isRunning) "PAUSE" else "START", fontSize = 18.sp) }
            Spacer(modifier = Modifier.width(16.dp))
            Button(modifier = Modifier.height(80.dp).width(120.dp), onClick = { elapsedTime = 0L; startTime = 0L; isRunning = false }) { Text(text = "RESET", fontSize = 18.sp) }
        }
    }
}