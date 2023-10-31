package com.example.bitreminder.CustomComponents

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.bitreminder.R


@Composable
fun CustomAlertDialog(title: String, subtitle: String, onConfirm: () -> Unit) {

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = title) },
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