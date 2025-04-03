package com.point.auth.authorization.presenter.viewmodel

import com.point.auth.authorization.domain.AuthorizeUseCase
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Action
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Event
import com.point.auth.common.data.AuthRequest
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val authorizeUseCase: AuthorizeUseCase,
) : MviViewModel<AuthState, AuthAction, AuthEvent>(
    initialValue = AuthState()
) {

    init {
        onAuthorizeClick()
    }

    override fun reduce(action: AuthAction, state: AuthState): AuthState = when (action) {
        is Action.OnLoginInput -> state.copy(login = action.value)
        is Action.OnPasswordInput -> state.copy(password = action.value)

        Event.OnFieldsEmpty -> state.copy(isInvalidCredentials = true)
        Event.OnAuthorizationFailed -> state.copy(isInvalidCredentials = true)

        Event.OnAuthorizationSuccess -> state.copy(isInvalidCredentials = false)

        Action.Authorization -> state
    }

    private fun onAuthorizeClick() {
        mapAction<Action.Authorization> {
            val login = state.login
            val password = state.password

            if (login.isEmpty() || password.isEmpty()) {
                return@mapAction Event.OnFieldsEmpty
            }

            authorizeUseCase(
                authRequest = AuthRequest(
                    username = state.login,
                    password = state.password,
                )
            ).fold(
                onSuccess = {
                    emitEvent(AuthEvent.NavigateAllChats)
                    Event.OnAuthorizationSuccess
                },
                onFailure = {
                    it.printStackTrace()
                    Event.OnAuthorizationFailed
                }
            )
        }
    }

}