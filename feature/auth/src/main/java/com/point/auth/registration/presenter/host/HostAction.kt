package com.point.auth.registration.presenter.host

sealed interface HostAction {

    sealed interface Action : HostAction {

        data class OnNewPage(val old: Int, val new: Int) : Action

        data object OnRegistration : Action

    }
}