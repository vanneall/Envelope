package com.point.navigation

import android.net.Uri

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

        data object SearchChats : ChatsFeature

        data object ChatsGroupCreation : ChatsFeature

        data class GroupChatCreationConfirmation(val ids: List<String>) : ChatsFeature

        data object Gallery : ChatsFeature

        data object Camera : ChatsFeature

        data class Photo(val uri: Uri) : ChatsFeature
    }

    sealed interface SettingsFeature : Route {

        data object Settings : SettingsFeature

        data object ProfileEdit : SettingsFeature
    }
}