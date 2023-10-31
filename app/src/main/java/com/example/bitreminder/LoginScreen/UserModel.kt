package com.example.bitreminder.LoginScreen

@kotlinx.serialization.Serializable
data class UserModel(
    var id: String,
    var user_name: String?,
    var user_email: String,
    var password: String?,
    var repeat_password: String?
)

@kotlinx.serialization.Serializable
data class User(
    var id: String,
    var user_name: String?,
    var user_email: String?
)

@kotlinx.serialization.Serializable
data class Album(
    var id: Int?,
    var album_name: String?,
    var profile_id: String?
)