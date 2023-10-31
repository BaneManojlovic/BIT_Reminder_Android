package com.example.bitreminder.SettingsScreen

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
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.gotrue

class SettingsViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    // Logout user
    fun logout(context: Context): UserState {
        val sharedPref = SharedPreferencesHelper(context)
        viewModelScope.launch {
            try {
                SupabaseClient.client.gotrue.logout()
                sharedPref.clearPreferences()
                _userState.value = UserState.Success("Logout success")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Logout error")
            }
        }
        return _userState.value
    }
}