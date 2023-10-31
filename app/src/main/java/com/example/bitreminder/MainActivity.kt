package com.example.bitreminder

import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.lifecycleScope
import com.example.bitreminder.API.AuthManager
import com.example.bitreminder.Helpers.SplashAuthHelper
import com.example.bitreminder.LoginScreen.Album
import com.example.bitreminder.LoginScreen.User
import com.example.bitreminder.LoginScreen.UserModel
import com.example.bitreminder.ui.theme.BITReminderTheme
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.security.Provider

class MainActivity : ComponentActivity() {

    val manager = AuthManager()
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
