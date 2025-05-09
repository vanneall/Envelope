package com.point.auth.registration.presenter.credentials

import com.point.auth.registration.presenter.credentials.CredentialsAction.Action
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CredentialsViewModel @Inject constructor() :
    MviViewModel<CredentialsState, CredentialsAction, Any>(
        initialValue = CredentialsState()
    ) {

    val userCredentials
        get() = UserCredentials(
            username = state.login,
            password = state.password,
            email = state.email,
            code = state.code,
        )

    override fun reduce(action: CredentialsAction, state: CredentialsState) = when (action) {
        is Action.OnLoginInput -> state.copy(login = action.value)
        is Action.OnPasswordInput -> state.copy(password = action.value)
        is Action.OnPasswordSecondInput -> state.copy(passwordSecondInput = action.value)
        Action.OnRegistration -> state
        is Action.OnEmailInput -> state.copy(email = action.value)
        is Action.OnCodeInput -> state.copy(code = action.value)
    }

    fun isRegistrationValid() = state.login.isNotEmpty()&&
            state.password.isNotEmpty() &&
            state.passwordSecondInput.isNotEmpty() &&
            state.password == state.passwordSecondInput
}
