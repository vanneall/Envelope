package com.point.auth.registration.presenter.mvi

sealed interface RegEvent {

    data class SwitchPage(val old: Int, val new: Int) : RegEvent

    data object NavigateToMainScreen : RegEvent
}