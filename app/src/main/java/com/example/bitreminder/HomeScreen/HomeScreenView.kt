package com.example.bitreminder.HomeScreen

import android.annotation.SuppressLint
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import com.example.bitreminder.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenView(navController: NavController) {

    val context = LocalContext.current
    val reminders = listOf("Taskovi ponedeljak", "Lista za kupovinu", "Delovi", "Pozivi", "Taskovi ponedeljak1", "Lista za kupovinu1", "Delovi1", "Pozivi1")

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
                        Toast.makeText(context, "Add New Reminder tapped", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            painterResource(id = R.drawable.baseline_add_24),
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
                        "Reminders",
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
            items(reminders) {
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
                    Spacer(modifier = Modifier.width(1.dp))
                    Column(modifier = Modifier
                        .width(300.dp)
                        .padding(10.dp)
                        .background(Color.Transparent)
                    ) {
                        Text(text = "$it",
                            modifier = Modifier
                                .height(20.dp),
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp)
                        Text(
                            text = "10.05.2023",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                    }
                    Icon(painter = painterResource(id = R.drawable.ic_star_36),
                        contentDescription = null,
                        tint = Color.Yellow)
                    Spacer(modifier = Modifier.width(4.dp))
                }


            }
        }

    }
}