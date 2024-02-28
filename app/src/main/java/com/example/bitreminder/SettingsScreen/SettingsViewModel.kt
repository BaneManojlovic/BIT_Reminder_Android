package com.example.bitreminder.SettingsScreen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitreminder.API.AuthManager
import com.example.bitreminder.LoginScreen.UserState
import com.example.bitreminder.Utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.bitreminder.API.SupabaseClient
import com.example.bitreminder.LoginScreen.User
import com.example.bitreminder.R
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import java.security.MessageDigest


class SettingsViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState
    var authManager = AuthManager()
    val client = authManager.getClient()

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

    fun generateUUIDFromUserUID(userUID: String): String {
        val bytes = userUID.toByteArray()
        val md5Digest = MessageDigest.getInstance("MD5")
        val md5HashBytes = md5Digest.digest(bytes)
        return md5HashBytes.joinToString("") { "%02x".format(it) }
    }

    fun getUserFromDatabase(context: Context) {
        val auth = SupabaseClient.client.gotrue
        viewModelScope.launch {
            try {
                val user = auth.currentUserOrNull()
                if (user != null) {
                    val myUser = User(
                        id = user?.id ?: "",
                        user_name = user.userMetadata.toString(),
                        user_email = user?.email ?: "")

                    val realUser = SupabaseClient.client.postgrest["profiles"].select { eq("id", user?.id ?: "") }.decodeSingle<User>()

                    saveUserDataInSharedPref(context, realUser)

                }
//                _userState.value = UserState.Success("Login success")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Login error")
            }
        }
    }

    private fun getToken(context: Context): String? {
        val sharedPref = SharedPreferencesHelper(context)
        return sharedPref.getStringData("accessToken")
    }

    // Save user in Shared Preferences
    private fun saveUserDataInSharedPref(context: Context, userModel: User) {
        val sharedPref = SharedPreferencesHelper(context)
        sharedPref.saveStringData("userName", userModel.user_name ?: "")
        sharedPref.saveStringData("userEmail", userModel.user_email)
    }

    fun getUserData(context: Context): User {
        val sharedPref = SharedPreferencesHelper(context)
        val userName = sharedPref.getUserName("userName")
        val userEmail = sharedPref.getUserEmail("userEmail")
        val token = sharedPref.getStringData("accessToken")
        return User(token ?: "", userName, userEmail ?: "")
    }
}