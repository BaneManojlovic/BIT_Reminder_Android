package com.example.bitreminder.Helpers

import android.Manifest
import android.annotation.SuppressLint
import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bitreminder.AlbumsScreen.AlbumsScreenView
import com.example.bitreminder.CustomComponents.CustomAlertDialog
import com.example.bitreminder.CustomComponents.CustomCancelYesAlertDialog
import com.example.bitreminder.CustomComponents.DialogWithTextField
import com.example.bitreminder.HomeScreen.HomeScreenView
import com.example.bitreminder.LoginScreen.LoginScreenView
import com.example.bitreminder.LoginScreen.LoginUserViewModel
import com.example.bitreminder.LoginScreen.UserState
import com.example.bitreminder.MainActivity
import com.example.bitreminder.MapScreen.MapScreenView
import com.example.bitreminder.PrivacyPolicyScreen.PrivacyPolicyScreen
import com.example.bitreminder.R
import com.example.bitreminder.RegistrationScreen.RegistrationScreenView
import com.example.bitreminder.SettingsScreen.SettingsScreenView

sealed class Destination(val route: String) {
    object Login: Destination("login")
    object Registration: Destination("registration")
    object Home: Destination("Home")
    object Map: Destination("Map")
    object Albums: Destination("Albums")
    object Settings: Destination("Settings")
    object PrivacyPolicy: Destination("privacy")
}


@Composable
fun SplashAuthHelper(viewModel: SplashViewModel = viewModel()) {

    // Take current context
    val context = LocalContext.current
    val userState by viewModel.userState

    // Set initial flow for the application
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorResource(R.color.background_blue_color)
    ) {
        viewModel.isUserLoggedIn(context)
        // Flow by UserState
        when (userState) {
            is UserState.Loading -> {
                println("Loading...")
            }

            is UserState.Error -> {
                println("User is not logged in")
                val navController = rememberNavController()
                NavigationAppHost(navController = navController)
            }

            is UserState.Success -> {
                println("User is logged in")
                val navController = rememberNavController()
                HomeBottomNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun NavigationAppHost(navController: NavHostController) {
    // Take current context
    val context = LocalContext.current
    // Set Navigation Host for authentification screen flow Splash -> Login -> Register -> Home
    NavHost(navController = navController, startDestination = "login") {
        composable(Destination.Login.route) { LoginScreenView(navController = navController) }
        composable(Destination.Registration.route) { RegistrationScreenView(navController = navController) }
        composable(Destination.Home.route) {
            // set Home screen as root
            val homeNavController = rememberNavController()
            HomeBottomNavigation(navController = homeNavController)
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeBottomNavigation(navController: NavHostController) {
    // Take current context
    val context = LocalContext.current
    var isLocationPermissionGranted = remember { mutableStateOf(false) }
    var toOpenLocationSettingsScreen = remember { mutableStateOf(false) }

    // Setting array of permissions
    val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)
    // Creating launcher
    val launcherForMultiplePermissions = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionsMap ->
            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                isLocationPermissionGranted.value = true
                toOpenLocationSettingsScreen.value = false
                navController.navigate(Destination.Map.route) {
                    popUpTo(Destination.Map.route)
                    launchSingleTop = true
                }
            } else {
                isLocationPermissionGranted.value = false
                // Redirect to open Location Settings screen
                toOpenLocationSettingsScreen.value = true
            }
        }
    )

    // Set UI
    Scaffold(bottomBar = {
        BottomNavigation(
            backgroundColor = colorResource(id = R.color.textfield_blue_color)

        ) {
            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry.value?.destination
            // setup Home Tab
            BottomNavigationItem(
                selected = currentDestination?.route == Destination.Home.route,
                unselectedContentColor = Color.Gray,
                selectedContentColor = Color.White,
                onClick = {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Home.route)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painter = painterResource(id = R.drawable.baseline_home_32), contentDescription = null) },
                label = { Text(text = Destination.Home.route) }
            )
            // setup Map Tab
            BottomNavigationItem(
                selected = currentDestination?.route == Destination.Map.route,
                unselectedContentColor = Color.Gray,
                selectedContentColor = Color.White,
                onClick = {
                          checkAndRequestLocationPermissions(
                              context,
                              permissions,
                              launcherForMultiplePermissions,
                              navController
                          )
                },
                icon = { Icon(painter = painterResource(id = R.drawable.ic_map_32), contentDescription = null) },
                label = { Text(text = Destination.Map.route) }
            )
            // setup Album Tab
            BottomNavigationItem(
                selected = currentDestination?.route == Destination.Albums.route,
                unselectedContentColor = Color.Gray,
                selectedContentColor = Color.White,
                onClick = {
                    navController.navigate(Destination.Albums.route) {
                        popUpTo(Destination.Albums.route)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painterResource(id = R.drawable.ic_albums_32), contentDescription = null) },
                label = { Text(text = Destination.Albums.route) }
            )
            // setup Settings Tab
            BottomNavigationItem(
                selected = currentDestination?.route == Destination.Settings.route,
                unselectedContentColor = Color.Gray,
                selectedContentColor = Color.White,
                onClick = {
                    navController.navigate(Destination.Settings.route) {
                        popUpTo(Destination.Settings.route)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painterResource(id = R.drawable.baseline_settings_24), contentDescription = null) },
                label = { Text(text = Destination.Settings.route) }
            )
        }
    }
    ) {
        // Set Navigation Host for authentification screen flow Splash -> Home -> Bottom Navigation
        NavHost(navController = navController,
            startDestination = "home",
            exitTransition = { ExitTransition.None },
            enterTransition = { EnterTransition.None }
            ) {
            composable(Destination.Home.route) { HomeScreenView(navController = navController) }
            composable(Destination.Map.route) { MapScreenView() }
            composable(Destination.Albums.route) { AlbumsScreenView() }
            composable(Destination.Settings.route) { SettingsScreenView(navController = navController) }
            composable(Destination.PrivacyPolicy.route) { PrivacyPolicyScreen() }
        }
    }

    if (toOpenLocationSettingsScreen.value) {
        CustomCancelYesAlertDialog(
            title = "You Denied permission for Location",
            subtitle = "Open Location Settings to allow giving location",
            onConfirm = {
                toOpenLocationSettingsScreen.value = false
                val i = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(i)
            },
            onDissmiss = {
                toOpenLocationSettingsScreen.value = false
            })
    }
}

fun checkAndRequestLocationPermissions(context: Context,
                                       permissions: Array<String>,
                                       launcherForMultiplePermissions: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
                                       navController: NavHostController
) {
    if(permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED }) {
        // Use location it is granted
        navController.navigate(Destination.Map.route) {
            popUpTo(Destination.Map.route)
            launchSingleTop = true
        }
    } else {
        // request location
        launcherForMultiplePermissions.launch(permissions)
    }
}

