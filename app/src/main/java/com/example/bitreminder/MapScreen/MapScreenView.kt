package com.example.bitreminder.MapScreen

import android.content.Context
import android.widget.ImageButton
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.example.bitreminder.CustomComponents.CustomAlertDialog
import com.example.bitreminder.Helpers.Destination
import com.example.bitreminder.Helpers.LoadingAnimation
import com.example.bitreminder.Helpers.getCurrentLocation
import com.example.bitreminder.R
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapScreenView(navController: NavController, entry: NavBackStackEntry) {

//    val favoriteLocationsList = entry.savedStateHandle.get<LatLng>("favoriteLocations")
    // Change this, to use locations list
        val favoriteLocationsList = entry.savedStateHandle.get<String>("favoriteLocations")


    val context = LocalContext.current
    var showMap by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    var choosenLatLngList = SnapshotStateList<LatLng>()
    var textToShow by remember { mutableStateOf("") }

    getCurrentLocation(context = context) {
        location = it
        showMap = true
    }

//    favoriteLocationsList?.let {
//        choosenLatLngList.addAll(listOf(favoriteLocationsList))
//    }
    // TODO: - Change this, to use locations list
    favoriteLocationsList?.let {
        textToShow = favoriteLocationsList
    }

    if (showMap) {
        CustomMap(
            context = context,
            text = textToShow,
            latLng = location,
            latLngListFromFav = choosenLatLngList,
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
            navController = navController
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingAnimation()
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Loading Map ...")
        }
    }
}

@Composable
fun CustomMap(
    context: Context,
    text: String, // TODO: - Remove this is just for test
    latLng: LatLng,
    latLngListFromFav: SnapshotStateList<LatLng>,
    uiSettings: MapUiSettings,
    mapProperties: MapProperties = MapProperties(),
    navController: NavController
) {

    var latLngList = remember { mutableStateListOf(latLng) }
    val showMyLocation = remember { mutableStateOf(false) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 15f)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings
        ) {
            // Check how many locations there are in latLng array
            if (latLngList.count() == 1) {
                Marker(
                    state = MarkerState(position = latLng),
                    title = "Location, " + latLngList.indexOf(latLng))
                cameraPositionState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
            } else {
                latLngList.toList().forEach {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Location, " + latLngList.indexOf(it))
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
                }
            }
        }
    }

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                ),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            OutlinedButton(
                modifier = Modifier
                    .size(50.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.background_blue_color)),
                shape = CircleShape,
                onClick = {
                    Toast.makeText(context, "Close / Remove favorite locations pins from map.", Toast.LENGTH_SHORT).show()
                    // Clean all pins on map except one on my current location
//                    if (latLngList.count() > 1) {
//                        latLngList.removeAll(choosenFavoritesList)
//                    }
                    // Reposition back on my current Location
                    showMyLocation.value = true
                }
            ) {
                Icon(painter = painterResource(R.drawable.baseline_close_24),
                    tint = Color.White,
                    contentDescription = "")
            }
            Spacer(modifier = Modifier.height(15.dp))
        OutlinedButton(
            modifier = Modifier
            .size(50.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.background_blue_color)),
            shape = CircleShape,
            onClick = {
                // Reposition back on my current Location
                showMyLocation.value = true
            }) {
                Icon(painter = painterResource(R.drawable.ic_my_location_24),
                    tint = Color.White,
                    contentDescription = "")
            }
            Spacer(modifier = Modifier.height(15.dp))
        OutlinedButton(
            modifier = Modifier
            .size(50.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.background_blue_color)),
            shape = CircleShape,
            onClick = {
                Toast.makeText(context, "Open Favorite Locations screen", Toast.LENGTH_SHORT).show()
                navController.navigate(Destination.FavoriteLocations.route)
            }) {
                Icon(painter = painterResource(R.drawable.baseline_favorite_border_24),
                    tint = Color.White,
                    contentDescription = "")
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    // For repositioning back to my current location
    if (showMyLocation.value) {
        CustomMap(
            context = context,
            text = "", // TODO: - Remove this is just for test
            latLng = latLngList[0],
            latLngListFromFav = SnapshotStateList<LatLng>(),
            uiSettings = MapUiSettings(zoomControlsEnabled = false),
            navController = navController
        )
    }
    // TODO: - Remove this is just for test
    if (text != "") {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
