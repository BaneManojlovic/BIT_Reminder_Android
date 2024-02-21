package com.example.bitreminder.AlbumsScreen

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
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
import androidx.core.content.ContextCompat
import com.example.bitreminder.CustomComponents.DialogWithTextField
import com.example.bitreminder.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AlbumsScreenView() {
    // Set Camera permission
    val cameraPermission = permission.CAMERA
    // Set local context
    val context = LocalContext.current
    // Method for checking allowed permission
    fun checkPermissionFor(permission: String): Boolean = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    // Properties
    var isCameraPermissionGranted by remember { mutableStateOf(checkPermissionFor(cameraPermission)) }
    var isAddNewAlbumSelected  = remember { mutableStateOf(false) }
    // TODO: - Remove this dummy data, when you implement real API
    val albums = listOf("Peki", "Travel", "Mountains", "Test test ...", "Peki1", "Travel1", "Mountains1", "Test test 1")
    // Create launcher for asking user for camera Permission.
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult = { isGranted ->
        if (isGranted) {
            Log.d("TAG", "Cameras $isGranted")
            isCameraPermissionGranted = true
            // Open Custom Alert Pop-Up for Creating New Album
            isAddNewAlbumSelected.value = true
        }
    })

    // UI
    Scaffold(
        modifier = Modifier
            .background(color = colorResource(R.color.background_blue_color)),
        backgroundColor = colorResource(id = R.color.background_blue_color),

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
                        .padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,

                    ) {
                    // add new album icon
                    IconButton(onClick = {
                        // Check is permission given by user
                        if (!isCameraPermissionGranted) {
                            // Ask permission for use camera
                            launcher.launch(cameraPermission)
                        } else {
                            // Permission is given continue to next step - allow opening Alert dialog for creating new album
                            isAddNewAlbumSelected.value = !isAddNewAlbumSelected.value
                        }
                    }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_create_new_folder_24),
                            contentDescription = null,
                            tint = Color.White)
                    }
                }
                // for title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp, top = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "Albums",
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White)
                }
            }
        },
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(colorResource(id = R.color.light_grey_divider_color))
            .alpha(0.1f)) {}
        LazyColumn(modifier = Modifier
            // .background(colorResource(id = R.color.tableview_cell_blue_color))
            .fillMaxWidth()
        ) {
            items(albums) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(12.dp)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(colorResource(id = R.color.tableview_cell_blue_color))
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    "Go to $it Album Details screen",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
//                            navController.navigate(Destination.PrivacyPolicy.route)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row(modifier = Modifier
                        .width(300.dp)
                        .padding(10.dp)
                        .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_folder_48),
                            contentDescription = null,
                            tint = Color.White)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "$it",
                            modifier = Modifier
                                .height(20.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal, fontSize = 16.sp
                        )
                    }
                    Icon(painter = painterResource(id = R.drawable.ic_next_bigger),
                        contentDescription = null,
                        tint = Color.White)
                }

            }
        }
    }

    // Handling Alert dialog for creating new album
    if (isAddNewAlbumSelected.value) {
        DialogWithTextField(
            title = "Create new album",
            subtitle = "Enter album title:",
            onDismiss = {
                isAddNewAlbumSelected.value = false
            },
            onConfirm = {
                // TODO: - pozovi metodu iz viewModela za kreiranje novog albuma
                // viewModel.logout(context)
                isAddNewAlbumSelected.value = false
            }
        )
    }
}
