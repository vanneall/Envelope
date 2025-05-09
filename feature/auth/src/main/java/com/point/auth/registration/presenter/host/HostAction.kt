package com.point.auth.registration.presenter.host

sealed interface HostAction {

    sealed interface UiAction : HostAction {

        data class OnNewPage(val old: Int, val new: Int) : UiAction

        data object OnRegistration : UiAction

        class RequestCode(val email: String) : UiAction

    }
}