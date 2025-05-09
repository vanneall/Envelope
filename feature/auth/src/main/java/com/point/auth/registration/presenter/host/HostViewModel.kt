package com.point.auth.registration.presenter.host

import com.point.auth.registration.domain.usecase.RegistrationUseCase
import com.point.auth.registration.presenter.credentials.CredentialsViewModel
import com.point.auth.registration.presenter.host.HostAction.UiAction
import com.point.auth.registration.presenter.profile.RegistrationProfileViewModel
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.point.core.services.auth.models.SignupData
import ru.point.core.services.auth.repository.AuthorizationRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val registrationUseCase: RegistrationUseCase,
    private val authorizationRepository: AuthorizationRepository,
) : MviViewModel<RegState, HostAction, HostEvent>(
    initialValue = RegState()
) {

    lateinit var regProfileViewModel: RegistrationProfileViewModel
    lateinit var credentialsViewModel: CredentialsViewModel

    init {
        onRegistration()
        onNewPage()
        onRequestCode()
    }

    private fun onNewPage() {
        handleAction<UiAction.OnNewPage> { action ->
            when {
                action.old == 0 -> HostEvent.SwitchPage(0, action.new)
                action.old > action.new -> HostEvent.SwitchPage(action.old, action.new)
                action.old == 1 && regProfileViewModel.isValid() -> HostEvent.SwitchPage(action.old, action.new)
                else -> null
            }?.let { emitEvent(it) } ?: Timber.tag(TAG).d("No scroll")
        }
    }

    private fun onRequestCode() {
        handleAction<UiAction.RequestCode> { action ->
            authorizationRepository.requestCode(action.email)
        }
    }

    private fun onRegistration() {
        handleAction<UiAction.OnRegistration> {
            if (!credentialsViewModel.isRegistrationValid()) return@handleAction

            val userCredentials = credentialsViewModel.userCredentials
            val userData = regProfileViewModel.userData

            registrationUseCase(
                SignupData(
                    login = userCredentials.username,
                    password = userCredentials.password,
                    name = userData.name,
                    status = userData.status,
                    about = userData.about,
                    uri = userData.uri,
                    email = userCredentials.email,
                    code = userCredentials.code,
                )
            ).fold(
                onSuccess = { emitEvent(HostEvent.NavigateToMainScreen) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    companion object {

        private const val TAG = "HostViewModel"

    }
}
