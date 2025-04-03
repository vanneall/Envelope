package com.point.auth.authorization.presenter.viewmodel

sealed interface AuthEvent {

    data object NavigateAllChats : AuthEvent
}