package com.example.bitreminder.LoginScreen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.bitreminder.API.SupabaseClient
import com.example.bitreminder.Utils.SharedPreferencesHelper
import io.github.jan.supabase.gotrue.gotrue

class LoginUserViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    // Login
    fun login(
        context: Context,
        userEmail: String,
        userPassword: String
    ): UserState {
        viewModelScope.launch {
            try {
                SupabaseClient.client.gotrue.loginWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                _userState.value = UserState.Success("Login success")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Login error")
            }
        }
        return _userState.value
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

    // Check is user logged in
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
}