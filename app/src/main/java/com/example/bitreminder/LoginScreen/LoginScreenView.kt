package com.example.bitreminder.LoginScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.bitreminder.Helpers.Destination

@Composable
fun LoginScreenView(navController: NavController) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            navController.navigate(Destination.Home.route)
        }) {
            Text(text = "Click this", color = Color.Red)
        }
    }
}