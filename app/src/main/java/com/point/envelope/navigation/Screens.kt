package com.point.envelope.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    sealed interface Main : Screen {

        @Serializable
        data object AllChats : Main

        @Serializable
        data object Profile : Main

        @Serializable
        data object Contacts : Main
    }

    sealed interface SubScreen : Screen {

        @Serializable
        data class Chat(val id: String) : SubScreen

        @Serializable
        data object Authorization : SubScreen

        @Serializable
        data object Registration : SubScreen

        @Serializable
        data object CreateNewChat : SubScreen

        @Serializable
        data object SearchContacts : SubScreen

        @Serializable
        data object NotificationContacts : SubScreen

        @Serializable
        data class UserProfile(val userId: String) : SubScreen
    }
}