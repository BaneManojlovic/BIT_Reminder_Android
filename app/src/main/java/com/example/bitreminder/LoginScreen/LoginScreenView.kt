package com.example.bitreminder.LoginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.example.bitreminder.Helpers.Destination
import com.example.bitreminder.R
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.lifecycleScope
import com.example.bitreminder.MainActivity

@Composable
fun LoginScreenView(navController: NavController,
                    viewModel: LoginUserViewModel = viewModel()) {
    // Take current context
    val context = LocalContext.current
    val userState by viewModel.userState

    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val eyeImage = painterResource(id = R.drawable.ic_password_lock_32)
    // Setup UI
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            viewModel.login(
                context,
                userEmail = "bane1manojlovic@gmail.com",
                userPassword = "BakiMaki106")
        }) {
            Text(text = "Click this", color = Color.Red)
        }
        // Flow by UserState
        when (userState) {
            is UserState.Loading -> {
                println("Loading...")
            }

            is UserState.Error -> {
                println("Error ...")
            }

            is UserState.Success -> {
                println("Login Success...")
                navController.navigate(Destination.Home.route)
            }
        }
    }

}