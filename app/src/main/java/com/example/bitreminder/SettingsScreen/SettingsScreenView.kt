package com.example.bitreminder.SettingsScreen

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bitreminder.CustomComponents.CustomAlertDialog
import com.example.bitreminder.CustomComponents.CustomCancelYesAlertDialog
import com.example.bitreminder.Helpers.Destination
import com.example.bitreminder.LoginScreen.LoginUserViewModel
import com.example.bitreminder.LoginScreen.UserState
import com.example.bitreminder.MainActivity
import com.example.bitreminder.R
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreenView(navController: NavController,
                       viewModel: SettingsViewModel = viewModel()) {

    val context = LocalContext.current
    val userState by viewModel.userState
    val openProfileDialog = remember { mutableStateOf(false) }
    val openLogoutUserDialog = remember { mutableStateOf(false) }
    val openDeleteAccountDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .background(color = colorResource(R.color.background_blue_color)),
        backgroundColor = Color.Red,

        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(112.dp)
                    .background(
                        colorResource(id = R.color.background_blue_color),
                        shape = RectangleShape
                    )
                    .padding(top = 8.dp, bottom = 0.dp, start = 4.dp, end = 4.dp)
            ) {
                // for icons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween) {}
                // for title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Settings",
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White)
                }
            }
        },
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(colorResource(id = R.color.background_blue_color)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(colorResource(id = R.color.light_grey_divider_color))
                .alpha(0.1f)) {}
            // Profile option
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.background_blue_color))
                .padding(start = 12.dp, end = 12.dp)
                .height(80.dp)
                .clickable {
                    openProfileDialog.value = true
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Profile",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start)
                Icon(painter = painterResource(id = R.drawable.ic_next_bigger),
                    tint = Color.White,
                    modifier = Modifier.height(60.dp),
                    contentDescription = null)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 12.dp, end = 12.dp)
                .background(colorResource(id = R.color.light_grey_divider_color))
                .alpha(0.1f)) {}
            // Privacy Policy option
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.background_blue_color))
                .padding(start = 12.dp, end = 12.dp)
                .height(80.dp)
                .clickable {
                    Toast.makeText(context, "Go to Privacy Policy screen", Toast.LENGTH_SHORT).show()
                    navController.navigate(Destination.PrivacyPolicy.route)
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Privacy Policy",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start)
                Icon(painter = painterResource(id = R.drawable.ic_next_bigger),
                    tint = Color.White,
                    modifier = Modifier.height(60.dp),
                    contentDescription = null)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 12.dp, end = 12.dp)
                .background(colorResource(id = R.color.light_grey_divider_color))
                .alpha(0.1f)) {}
            // Logout option
            Row(modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.background_blue_color))
                .padding(start = 12.dp, end = 12.dp)
                .height(80.dp)
                .clickable {
                    openLogoutUserDialog.value = true
                },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Logout",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start)
                Icon(painter = painterResource(id = R.drawable.ic_next_bigger),
                    tint = Color.White,
                    modifier = Modifier.height(60.dp),
                    contentDescription = null)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 12.dp, end = 12.dp)
                .background(colorResource(id = R.color.light_grey_divider_color))
                .alpha(0.1f)) {}
            // Delete account option
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorResource(id = R.color.background_blue_color))
                    .padding(start = 12.dp, end = 12.dp)
                    .height(80.dp)
                    .clickable {
                        openDeleteAccountDialog.value = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Delete account",
                    color = colorResource(id = R.color.red_logo_color),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start)
                Icon(painter = painterResource(id = R.drawable.ic_next_bigger),
                    tint = Color.White,
                    modifier = Modifier.height(60.dp),
                    contentDescription = null)
            }
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 12.dp, end = 12.dp)
                .background(colorResource(id = R.color.light_grey_divider_color))
                .alpha(0.1f)) {}
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
                println("Logout Success...")
                Toast.makeText(context, "You logout Successfully!", Toast.LENGTH_SHORT).show()
                context.startActivity(Intent(context, MainActivity::class.java))
            }
        }
    }

    if (openProfileDialog.value) {
        CustomAlertDialog(
            title = "Petar Petrovic",
            subtitle = "petarpetrovic@gmail.com",
            onConfirm = {
                openProfileDialog.value = false
            })
    }

    if (openLogoutUserDialog.value) {
        CustomCancelYesAlertDialog(
            title = "Alert",
            subtitle = "Are you sure you want to logout?",
            onConfirm = {
                viewModel.logout(context)
                openLogoutUserDialog.value = false
            },
            onDissmiss = {
                openLogoutUserDialog.value = false
            })
    }

    if (openDeleteAccountDialog.value) {
        CustomCancelYesAlertDialog(
            title = "Alert",
            subtitle = "Are you sure you want to delete user account?",
            onConfirm = {
                Toast.makeText(context, "calling Delete user API ...", Toast.LENGTH_SHORT).show()
                openDeleteAccountDialog.value = false
            },
            onDissmiss = {
                openDeleteAccountDialog.value = false
            })
    }
}