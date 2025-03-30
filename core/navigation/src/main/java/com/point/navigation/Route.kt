package com.point.navigation

sealed interface Route {

    sealed interface AuthFeature : Route {

        data object Authorization : AuthFeature

        data object Registration : AuthFeature

    }

    sealed interface ContactsFeature : Route {

        data object UserContacts : ContactsFeature

        data object ContactsSearch : ContactsFeature

        data object ContactsRequests : ContactsFeature

        data class UserProfile(val username: String) : ContactsFeature

    }

    sealed interface ChatsFeature : Route {

        data object Chats : ChatsFeature

        data class Messaging(val chatId: String) : ChatsFeature
    }

    sealed interface SettingsFeature : Route {

        data object Settings : SettingsFeature

    }
}