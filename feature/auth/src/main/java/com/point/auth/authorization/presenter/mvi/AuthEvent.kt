package com.point.auth.authorization.presenter.mvi

sealed interface AuthEvent {

    data object NavigateAllChats : AuthEvent
}