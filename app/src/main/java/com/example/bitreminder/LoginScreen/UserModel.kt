package com.example.bitreminder.LoginScreen

import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    var id: String,
    var user_name: String?,
    var user_email: String,
    var password: String?,
    var repeat_password: String?
)

@Serializable
data class User(
    var id: String,
    var user_name: String?,
    var user_email: String
)

@Serializable
data class Album(
    var id: Int?,
    var album_name: String?,
    var profile_id: String?
)