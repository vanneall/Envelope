package com.point.contacts.search.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddContactRequest(
    @SerialName("id")
    val id: String,
)