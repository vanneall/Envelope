package com.point.auth.authorization.presenter.viewmodel

import com.point.auth.R
import com.point.auth.authorization.domain.AuthorizeUseCase
import com.point.auth.authorization.presenter.viewmodel.AuthAction.Action
import com.point.auth.authorization.presenter.viewmodel.AuthAction.SideEffect
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.point.core.services.auth.models.SigninData
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

        is SideEffect.BothFieldInvalid -> state.copy(
            isLoginInvalid = true,
            loginInvalidReason = action.reason,
            isPasswordInvalid = true,
            passwordInvalidReason = action.reason,
        )

        is SideEffect.OnLoginFieldInvalid -> state.copy(
            isLoginInvalid = true,
            loginInvalidReason = action.reason,
        )

        is SideEffect.OnPasswordFieldInvalid -> state.copy(
            isPasswordInvalid = true,
            loginInvalidReason = action.reason,
        )

        SideEffect.OnAuthorizationFailed -> state.copy()

        SideEffect.OnAuthorizationSuccess -> state.copy()

        Action.Authorization -> state
    }

    private fun onAuthorizeClick() {
        mapAction<Action.Authorization> {
            val login = state.login
            val password = state.password

            val sideEffect = when {
                login.isEmpty() && password.isEmpty() -> SideEffect.BothFieldInvalid(R.string.field_cant_be_empty)
                login.isEmpty() -> SideEffect.OnLoginFieldInvalid(R.string.field_cant_be_empty)
                password.isEmpty() -> SideEffect.OnLoginFieldInvalid(R.string.field_cant_be_empty)
                else -> null
            }

            if (sideEffect != null) return@mapAction sideEffect

            authorizeUseCase(
                signinData = SigninData(
                    username = state.login,
                    password = state.password,
                )
            ).fold(
                onSuccess = {
                    emitEvent(AuthEvent.NavigateAllChats)
                    SideEffect.OnAuthorizationSuccess
                },
                onFailure = {
                    it.printStackTrace()
                    SideEffect.OnAuthorizationFailed
                }
            )
        }
    }

}