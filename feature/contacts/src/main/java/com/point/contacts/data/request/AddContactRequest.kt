package com.point.contacts.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddContactRequest(
    @SerialName("id")
    val id: String,
)