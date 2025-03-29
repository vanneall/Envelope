package com.point.contacts.profile.viewmodel

import androidx.compose.runtime.Immutable
import java.time.LocalDate
import java.time.LocalDateTime

@Immutable
data class ProfileState(
    val isInitialLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val isRefreshingEnable: Boolean = false,
    val username: String = "",
    val name: String = "",
    val lastSeen: LocalDateTime? = null,
    val status: String? = null,
    val about: String? = null,
    val birthDate: LocalDate? = null,
    val photos: List<Long> = emptyList(),
    private val userInContacts: Boolean = false,
    private val userInSent: Boolean = false,
) {
    val showSendMessage
        get() = userInContacts

    val showDeleteFromContacts
        get() = userInContacts

    val showAddToContacts
        get() = !userInContacts && !userInSent

    val showSentRequest
        get() = userInSent && !userInContacts
}