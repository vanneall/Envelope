package com.point.auth.registration.presenter.mvi

sealed interface RegAction {

    data class OnNewPage(val old: Int, val new: Int) : RegAction

    data object OnRegistration : RegAction
}