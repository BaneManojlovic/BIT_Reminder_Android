package com.example.bitreminder.MapScreen

import android.annotation.SuppressLint
import android.graphics.fonts.FontStyle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bitreminder.R
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteLocationsView(navController: NavController) {

    val context = LocalContext.current
    var location by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    // TODO: - Change this to user real data - this is just for test
    var locationsList = listOf("pharmacy", "hospital", "bank", "ATM", "library", "movie theater", "gas station", "park", "bakery", "post office", "parking", "coffy shop", "market", "theater")
    var choosenFavoritesList = remember { mutableStateListOf(location) }  // ovo vracam nazad na map ekran!!!
    choosenFavoritesList.add(LatLng(45.24681190680794, 19.824497859831812))
    choosenFavoritesList.add(LatLng(45.24858482348816, 19.827350637660064))
    choosenFavoritesList.add(LatLng(45.245497681109384, 19.82536073707616))

    Scaffold(
        modifier = Modifier
            .background(color = colorResource(R.color.background_blue_color)),
        backgroundColor = colorResource(id = R.color.background_blue_color),
        topBar = {
            Text(modifier = Modifier
                .fillMaxWidth(),
                text = "Favorite Locations",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.White)
        }
    ) {
        LazyColumn(modifier = Modifier
            // .background(colorResource(id = R.color.tableview_cell_blue_color))
            .fillMaxWidth()
        ) {

            items(locationsList) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colorResource(id = R.color.light_grey_divider_color))
                    .alpha(0.1f)) {}
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(12.dp)
                        .height(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = R.color.background_blue_color))
                        .clickable {
                            // Prepare Map screen to accept location data
//                            navController.previousBackStackEntry?.savedStateHandle?.set("favoriteLocations", choosenFavoritesList)
                            // TODO: - Change this to send Locations list of choosen item from Favorite locations list
                            navController.previousBackStackEntry?.savedStateHandle?.set("favoriteLocations", "$it")
                            // Go back to Map screen
                            navController.popBackStack()
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.width(1.dp))
                    Column(modifier = Modifier
                        .width(300.dp)
                        .padding(10.dp)
                        .background(Color.Transparent)
                    ) {
                        androidx.compose.material3.Text(text = "$it",
                            modifier = Modifier
                                .height(20.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp)
                    }
                    Icon(painter = painterResource(id = R.drawable.ic_my_location_24),
                        contentDescription = null,
                        tint = Color.Red)
                    Spacer(modifier = Modifier.width(4.dp))
                }



            }



        }
    }
}