package com.point.envelope.navigation.navhost

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
        data object Authorization : SubRoute

        @Serializable
        data object Registration : SubRoute

        @Serializable
        data object SearchContacts : SubRoute

        @Serializable
        data object NotificationContacts : SubRoute

        @Serializable
        data class UserProfile(val username: String) : SubRoute

        @Serializable
        data object EditProfile : SubRoute
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

        SettingsFeature.Settings -> EntryRoute.Settings
        SettingsFeature.ProfileEdit -> SubRoute.EditProfile
    }