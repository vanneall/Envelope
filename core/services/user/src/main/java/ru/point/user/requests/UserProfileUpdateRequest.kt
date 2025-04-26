package ru.point.user.requests

import com.point.network.di.serializers.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
internal data class UserProfileUpdateRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("status")
    val status: String? = null,
    @SerialName("about")
    val about: String? = null,
    @Serializable(LocalDateSerializer::class)
    @SerialName("birth_date")
    val birthDate: LocalDate? = null,
)
