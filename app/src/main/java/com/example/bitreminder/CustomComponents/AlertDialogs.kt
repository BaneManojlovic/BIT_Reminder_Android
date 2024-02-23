package com.example.bitreminder.CustomComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bitreminder.R


@Composable
fun CustomAlertDialog(title: String, subtitle: String, onConfirm: () -> Unit) {

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp) },
        text = { Text(text = subtitle) },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_blue_color))
            ) {
                Text(text = "OK", color = Color.White)
            }
        })
}

@Composable
fun CustomCancelYesAlertDialog(
    title: String,
    subtitle: String,
    onDissmiss: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = title) },
        text = { Text(text = subtitle) },
        confirmButton = {
            Button(
                onClick = { onConfirm() },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_blue_color))
            ) {
                Text(text = "Yes", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = { onDissmiss() },
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_blue_color))
            ) {
                Text(text = "Cancel", color = Color.White)
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWithTextField(
    title: String,
    subtitle: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    var text by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismiss() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = colorResource(R.color.tableview_cell_blue_color)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp)
                Text(
                    text = subtitle,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    fontSize = 18.sp)

                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 0.dp),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_blue_color))
                    ) {
                        Text(text = "Cancel", color = Color.White)
                    }
                    Button(
                        onClick = { onConfirm() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.button_blue_color))
                    ) {
                        Text(text = "Create", color = Color.White)
                    }
                }
            }
        }
    }
}