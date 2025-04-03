package com.point.auth.registration.presenter.profile

import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationProfileViewModel @Inject constructor() : MviViewModel<
        RegProfileState,
        RegProfileAction,
        Any
        >(
    initialValue = RegProfileState()
) {

    val userData
        get() = UserData(
            name = state.name.trim(),
            status = state.status.trim().takeIf { it.isNotEmpty() },
            about = state.about.trim().takeIf { it.isNotEmpty() },
        )

    override fun reduce(action: RegProfileAction, state: RegProfileState) = when (action) {
        is RegProfileAction.OnNameInput -> state.copy(name = action.value)
        is RegProfileAction.OnStatusInput -> state.copy(status = action.value)
        is RegProfileAction.OnAboutInput -> state.copy(about = action.value)
    }

    fun isValid() = state.name.trim().isNotEmpty()
}
