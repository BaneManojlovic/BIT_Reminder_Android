package com.example.bitreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.bitreminder.Helpers.SplashAuthHelper
import com.example.bitreminder.ui.theme.BITReminderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set Thread to sleep & install Splash screen
        Thread.sleep(3000)
        installSplashScreen()
        // Set base content
        setContent {
            // Call for Splash Auth Helper
            SplashAuthHelper()
        }
    }
}
