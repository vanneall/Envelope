package com.point.chats.creation.data

data class User(
    val username: String,
    val name: String,
    val photoId: String?,
    val checked: Boolean,
)