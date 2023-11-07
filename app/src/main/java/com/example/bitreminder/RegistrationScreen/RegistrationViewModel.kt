package com.example.bitreminder.RegistrationScreen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitreminder.LoginScreen.UserState
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch
import io.github.jan.supabase.postgrest.postgrest
import com.example.bitreminder.API.SupabaseClient
import com.example.bitreminder.LoginScreen.User
import com.example.bitreminder.LoginScreen.UserModel
import com.example.bitreminder.Utils.SharedPreferencesHelper
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.user.UserInfo
import io.ktor.http.contentRangeHeaderValue
import kotlin.Exception

class RegistrationViewModel: ViewModel() {

    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    // SignUp new user
    fun signUp(
        context: Context,
        userName: String,
        userEmail: String,
        userPassword: String
    ) {
        viewModelScope.launch {
            try {
                SupabaseClient.client.gotrue.signUpWith(Email) {
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                val token = getToken(context) ?: ""
                val userInfo = SupabaseClient.client.gotrue.retrieveUser(token)
                // Save user in database on backend
                saveUser(context, userInfo, userName)
                _userState.value = UserState.Success("Register User Success")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Register Failure")
            }
        }
    }

    fun saveUser(context: Context, user: UserInfo, userName: String) {
        viewModelScope.launch {
            try {
                _userState.value = UserState.Loading
                val user = User(id = user.id, user_name = userName, user_email = user.email ?: "")
                SupabaseClient.client.postgrest["profiles"].insert(user)
                // Save user data in Shared Preferences
                saveUserDataInSharedPref(context, user)
                _userState.value = UserState.Success("Success...")
            } catch (e: Exception) {
                _userState.value = UserState.Error("Error")
            }
        }
    }

    // Save user in Shared Preferences
    private fun saveUserDataInSharedPref(context: Context, userModel: User) {
        val sharedPref = SharedPreferencesHelper(context)
        sharedPref.saveStringData("userName", userModel.user_name ?: "")
        sharedPref.saveStringData("userEmail", userModel.user_email)
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