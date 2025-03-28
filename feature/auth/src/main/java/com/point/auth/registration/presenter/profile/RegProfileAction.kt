package com.point.auth.registration.presenter.profile

sealed interface RegProfileAction {

    data class OnNameInput(val value: String) : RegProfileAction

    data class OnStatusInput(val value: String) : RegProfileAction

    data class OnAboutInput(val value: String) : RegProfileAction

}