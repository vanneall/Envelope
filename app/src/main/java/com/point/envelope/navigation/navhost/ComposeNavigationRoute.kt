package com.point.envelope.navigation.navhost

import android.net.Uri
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.EntryRoute
import com.point.envelope.navigation.navhost.ComposeNavigationRoute.SubRoute
import com.point.navigation.Route
import com.point.navigation.Route.AuthFeature
import com.point.navigation.Route.ChatsFeature
import com.point.navigation.Route.ContactsFeature
import com.point.navigation.Route.SettingsFeature
import kotlinx.serialization.Serializable

sealed interface ComposeNavigationRoute {

    sealed interface EntryRoute : ComposeNavigationRoute {

        @Serializable
        data object Chats : EntryRoute

        @Serializable
        data object Contacts : EntryRoute

        @Serializable
        data object Settings : EntryRoute
    }

    sealed interface SubRoute : ComposeNavigationRoute {

        @Serializable
        data class Messaging(val chatId: String) : SubRoute

        @Serializable
        data object ChatCreation : SubRoute

        @Serializable
        data class MultiChatInfo(val chatId: String) : SubRoute

        @Serializable
        data object Authorization : SubRoute

        @Serializable
        data object Registration : SubRoute

        @Serializable
        data object SearchContacts : SubRoute

        @Serializable
        data object SearchChats : SubRoute

        @Serializable
        data object NotificationContacts : SubRoute

        @Serializable
        data class UserProfile(val username: String) : SubRoute

        @Serializable
        data object ChatCreationGroup : SubRoute

        @Serializable
        data class EditProfile(val username: String) : SubRoute

        @Serializable
        data class GroupChatCreationConfirm(val ids: List<String>) : SubRoute

        @Serializable
        data object Camera : SubRoute

        @Serializable
        data object Gallery : SubRoute

        @Serializable
        data object Battery : SubRoute

        @Serializable
        data object Appearance : SubRoute

        @Serializable
        data class Photo(val uri: String) : SubRoute
    }
}

val Route.asComposeRoute: ComposeNavigationRoute
    get() = when (this) {
        AuthFeature.Authorization -> SubRoute.Authorization
        AuthFeature.Registration -> SubRoute.Registration

        ContactsFeature.UserContacts -> EntryRoute.Contacts
        ContactsFeature.ContactsSearch -> SubRoute.SearchContacts
        ContactsFeature.ContactsRequests -> SubRoute.NotificationContacts
        is ContactsFeature.UserProfile -> SubRoute.UserProfile(username)

        ChatsFeature.Chats -> EntryRoute.Chats
        is ChatsFeature.Messaging -> SubRoute.Messaging(chatId)
        ChatsFeature.SearchChats -> SubRoute.SearchChats
        ChatsFeature.ChatsGroupCreation -> SubRoute.ChatCreationGroup
        is ChatsFeature.GroupChatCreationConfirmation -> SubRoute.GroupChatCreationConfirm(ids)
        is ChatsFeature.Camera -> SubRoute.Camera
        is ChatsFeature.Gallery -> SubRoute.Gallery
        is ChatsFeature.Photo -> SubRoute.Photo(Uri.encode(uri.toString()))

        SettingsFeature.Settings -> EntryRoute.Settings
        is SettingsFeature.ProfileEdit -> SubRoute.EditProfile(username = username)
        is SettingsFeature.Battery -> SubRoute.Battery
        is SettingsFeature.Appearance -> SubRoute.Appearance
    }