package com.point.auth.registration.presenter.mvi


import com.point.auth.authorization.data.UserRegistrationRequest
import com.point.auth.authorization.domain.AuthorizeUseCase
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: AuthorizeUseCase,
) : MviViewModel<RegState, RegAction, Any>(
    initialValue = RegState()
) {

    init {
        onRegistration()
    }

    override fun reduce(action: RegAction, state: RegState): RegState = when (action) {
        is RegAction.Action.OnLoginInput -> state.copy(login = action.value)
        is RegAction.Action.OnPasswordInput -> state.copy(password = action.value)
        is RegAction.Action.OnNameInput -> state.copy(name = action.value)

        RegAction.Action.OnLoginClear -> state.copy(login = "")
        RegAction.Action.OnPasswordClear -> state.copy(password = "")
        RegAction.Action.OnNameClear -> state.copy(name = "")

        RegAction.Action.OnRegistration -> state
    }

    private fun onRegistration() {
        handleAction<RegAction.Action.OnRegistration> {
            useCase.execute2(
                UserRegistrationRequest(
                    username = state.login,
                    password = state.password,
                    isDeveloper = true,
                )
            )
        }
    }

}