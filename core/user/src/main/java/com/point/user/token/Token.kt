package com.point.user.token

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Token(@SerialName("access_token")val value: String)