package com.point.auth.authorization.presenter.mvi

import com.point.viewmodel.MviViewModel

class AuthorizationViewModel : MviViewModel<AuthState, AuthAction, Any>(
    initialValue = AuthState()
) {
}