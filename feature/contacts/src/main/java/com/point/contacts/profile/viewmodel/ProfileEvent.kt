package com.point.contacts.profile.viewmodel

interface ProfileEvent {

    data class NavigateToChat(val id: String) : ProfileEvent

}