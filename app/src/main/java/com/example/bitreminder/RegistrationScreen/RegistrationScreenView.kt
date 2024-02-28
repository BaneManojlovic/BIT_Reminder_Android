package com.example.bitreminder.RegistrationScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bitreminder.Helpers.Destination
import com.example.bitreminder.LoginScreen.LoginUserViewModel
import com.example.bitreminder.LoginScreen.UserState
import com.example.bitreminder.R

@Composable
fun RegistrationScreenView(navController: NavController,
                           viewModel: RegistrationViewModel = viewModel()) {

    val context = LocalContext.current
    val userState by viewModel.userState

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var repeatedPassword by remember { mutableStateOf(TextFieldValue("")) }
    val eyeImage = painterResource(id = R.drawable.ic_password_lock_32)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Register",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { newName ->
                name = newName
            },
            placeholder = {
                Text(
                    text = "user name",
                    color = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(R.color.textfield_blue_color)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { newEmail ->
                email = newEmail
            },
            placeholder = {
                Text(
                    text = "email",
                    color = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = colorResource(R.color.textfield_blue_color)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
            },
            placeholder = {
                Text(
                    text = "password",
                    color = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                IconButton(modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                    onClick = { println("tap tap") }) {
                    Icon(eyeImage, contentDescription = null, tint = Color.White)
                }
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(R.color.textfield_blue_color))
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = repeatedPassword,
            onValueChange = { newRepPassword ->
                repeatedPassword = newRepPassword
            },
            placeholder = {
                Text(
                    text = "repeat password",
                    color = Color.LightGray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            trailingIcon = {
                IconButton(modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                    onClick = { println("tap tap") }) {
                    Icon(eyeImage, contentDescription = null, tint = Color.White)
                }
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = colorResource(R.color.textfield_blue_color))
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_blue_color)),
            onClick = {
                viewModel.signUp(
                    context,
                    userName = "Alen Alenovic",
                    userEmail = "alenko1234@gmail.com",
                    userPassword = "BakiMaki106"
                )
            }
        ) {
            Text(text = "Register", color = Color.White)
        }
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