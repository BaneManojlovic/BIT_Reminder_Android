package com.example.bitreminder.Helpers

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitreminder.LoginScreen.UserState
import com.example.bitreminder.Utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.bitreminder.API.SupabaseClient
import com.example.bitreminder.LoginScreen.User
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest

class SplashViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    fun isUserLoggedIn(
        context: Context
    ) {
        viewModelScope.launch {
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()) {
                    _userState.value = UserState.Error("User is not logged in")
                } else {
                    SupabaseClient.client.gotrue.retrieveUser(token)
                    SupabaseClient.client.gotrue.refreshCurrentSession()
                    saveToken(context)
                    _userState.value = UserState.Success("User is alerady logged in")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error")
            }
        }
    }

    // Token Save
    private fun saveToken(context: Context) {
        viewModelScope.launch {
            val accessToken = SupabaseClient.client.gotrue.currentAccessTokenOrNull() ?: ""
            val sharedPref = SharedPreferencesHelper(context)
            sharedPref.saveStringData("accessToken", accessToken)
        }
    }
    // Get Token
    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferencesHelper(context)
        return sharedPref.getStringData("accessToken")
    }
}