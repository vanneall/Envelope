package com.point.services.media.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class PhotoIdResponse(@SerialName("id") val id: Long)