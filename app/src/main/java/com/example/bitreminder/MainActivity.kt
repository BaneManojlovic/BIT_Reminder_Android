package com.example.bitreminder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import com.example.bitreminder.API.AuthManager
import com.example.bitreminder.CustomComponents.CustomAlertDialog
import com.example.bitreminder.CustomComponents.CustomCancelYesAlertDialog
import com.example.bitreminder.Helpers.ConnectionStatus
import com.example.bitreminder.Helpers.SplashAuthHelper
import com.example.bitreminder.Helpers.currentConnectivityStatus
import com.example.bitreminder.Helpers.observeConnectivityAsFlow


class MainActivity : ComponentActivity() {

    val manager = AuthManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Thread to sleep & install Splash screen
        Thread.sleep(3000)
        installSplashScreen()
        // Set base content
        setContent {
            // Check for network connection
            checkConnectivityStatus()
            // Call for Splash Auth Helper
            SplashAuthHelper()
        }
    }
}

@Composable
fun checkConnectivityStatus() {

    var openDialog by remember { mutableStateOf(false) }
    var closeDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val connection by connectivityStatus()
    val isConnected = connection === ConnectionStatus.Available

    if (isConnected) {
        if (closeDialog) {
            openDialog = false
        }
    } else {
        if (!closeDialog) {
            openDialog = true
        }
    }

    if (openDialog) {
        CustomAlertDialog(title = "No network connection",
            subtitle = "Please check your network conection!",
            onConfirm = {
                openDialog = false
                closeDialog = true
            })
    }
}

@Composable
fun connectivityStatus(): State<ConnectionStatus> {

    val context = LocalContext.current

    return produceState(initialValue = context.currentConnectivityStatus) {
        context.observeConnectivityAsFlow().collect { value = it }
    }
}