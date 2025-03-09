package com.point.contacts.search.data.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoShort(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("photo")
    val photo: Long?,
)