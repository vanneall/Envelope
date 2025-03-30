package com.example.settings.profile.viewmodel

sealed interface ProfileEditEvent {

    data object OnBack : ProfileEditEvent

}