package com.point.envelope.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object Authorization : Screen

    @Serializable
    data object Registration : Screen

    @Serializable
    data object AllChats : Screen

    @Serializable
    data class Chat(val id: String) : Screen

    @Serializable
    data object Profile : Screen

    @Serializable
    data object CreateNewChat : Screen

    @Serializable
    data object Contacts : Screen

    @Serializable
    data object SearchContacts : Screen

    @Serializable
    data object NotificationContacts : Screen
}