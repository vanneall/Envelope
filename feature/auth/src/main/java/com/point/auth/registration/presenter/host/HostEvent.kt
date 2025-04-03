package com.point.auth.registration.presenter.host

sealed interface HostEvent {

    data class SwitchPage(val old: Int, val new: Int) : HostEvent

    data object NavigateToMainScreen : HostEvent

}