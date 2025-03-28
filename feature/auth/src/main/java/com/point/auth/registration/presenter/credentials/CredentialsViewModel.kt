package com.point.auth.registration.presenter.credentials

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
        )

    override fun reduce(action: CredentialsAction, state: CredentialsState) = when (action) {
        is CredentialsAction.OnLoginInput -> state.copy(login = action.value)
        is CredentialsAction.OnPasswordInput -> state.copy(password = action.value)
        is CredentialsAction.OnRepasswordInput -> state.copy(repassword = action.value)
        CredentialsAction.OnRegistration -> state
    }

    fun isRegistrationValid() = state.login.isNotEmpty()&&
            state.password.isNotEmpty() &&
            state.repassword.isNotEmpty() &&
            state.password == state.repassword
}

data class UserCredentials(
    val username: String,
    val password: String,
)