package com.point.services.chats.models

data class ChatCreationData(
    val name: String?,
    val participants: List<String>
)