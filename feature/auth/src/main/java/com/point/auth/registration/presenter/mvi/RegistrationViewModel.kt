package com.point.auth.registration.presenter.mvi


import android.util.Log
import com.point.auth.authorization.domain.AuthorizeUseCase
import com.point.auth.registration.presenter.credentials.CredentialsViewModel
import com.point.auth.registration.presenter.profile.RegistrationProfileViewModel
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val useCase: AuthorizeUseCase,
) : MviViewModel<RegState, RegAction, RegEvent>(
    initialValue = RegState()
) {

    lateinit var regProfileViewModel: RegistrationProfileViewModel
    lateinit var credentialsViewModel: CredentialsViewModel

    init {
        onRegistration()
        onNewPage()
    }

    override fun reduce(action: RegAction, state: RegState): RegState = when (action) {
        else -> state
    }

    private fun onNewPage() {
        handleAction<RegAction.OnNewPage> { action ->
            if (action.old == 0) {
                emitEvent(RegEvent.SwitchPage(0, action.new))
            } else if (action.old > action.new) {
                emitEvent(RegEvent.SwitchPage(action.old, action.new))
            } else {
                if (action.old == 1 && regProfileViewModel.isValid()) {
                    emitEvent(RegEvent.SwitchPage(action.old, action.new))
                } else {
                    Log.d("mong", "no scroll")
                }
            }
        }
    }

    private fun onRegistration() {
        handleAction<RegAction.OnRegistration> {
            if (!credentialsViewModel.isRegistrationValid()) return@handleAction

            val userCredentials = credentialsViewModel.userCredentials
            val userData = regProfileViewModel.userData

            useCase.execute2(
                UserRegistrationData(
                    login = userCredentials.username,
                    password = userCredentials.password,
                    name = userData.name,
                    status = userData.status,
                    about = userData.about,
                )
            ).fold(
                onSuccess = { emitEvent(RegEvent.NavigateToMainScreen) },
                onFailure = { it.printStackTrace() }
            )
        }
    }
}

data class UserRegistrationData(
    val login: String,
    val password: String,
    val name: String,
    val status: String?,
    val about: String?,
)