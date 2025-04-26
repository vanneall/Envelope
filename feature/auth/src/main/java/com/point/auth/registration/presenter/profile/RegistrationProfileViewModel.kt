package com.point.auth.registration.presenter.profile

import com.point.auth.registration.presenter.profile.RegProfileAction.UiAction
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
            uri = state.uri,
        )

    override fun reduce(action: RegProfileAction, state: RegProfileState) = when (action) {
        is UiAction.OnPhotoPicked -> state.copy(uri = action.uri)
        is UiAction.OnNameInput -> state.copy(name = action.value)
        is UiAction.OnStatusInput -> state.copy(status = action.value)
        is UiAction.OnAboutInput -> state.copy(about = action.value)
    }

    fun isValid() = state.name.trim().isNotEmpty()
}
