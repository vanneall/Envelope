package com.example.settings.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.settings.data.ContactsRepository
import com.example.settings.data.UserProfileUpdateRequest
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : MviViewModel<ProfileEditState, ProfileEditAction, ProfileEditEvent>(
    initialValue = ProfileEditState()
) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchUserData().fold(
                onSuccess = {
                    emitAction(ProfileEditAction.OnDataFetchedSuccess(it))
                },
                onFailure = { it.printStackTrace() }
            )
        }

        handleOnSave()
    }

    override fun reduce(action: ProfileEditAction, state: ProfileEditState) = when (action) {

        is ProfileEditAction.OnDataFetchedSuccess -> state.copy(
            name = action.data.name,
            status = action.data.status.orEmpty(),
            about = action.data.about.orEmpty(),
        )

        is ProfileEditAction.OnNameEntered -> state.copy(name = action.value)
        is ProfileEditAction.OnStatusEntered -> state.copy(status = action.value)
        is ProfileEditAction.OnAboutEntered -> state.copy(about = action.value)

        ProfileEditAction.OnSavePressed,
        ProfileEditAction.OnSaveSuccessfully -> state
    }

    private fun handleOnSave() {
        handleAction<ProfileEditAction.OnSavePressed> {
            if (state.name.isEmpty()) return@handleAction

            contactsRepository.saveUserData(
                UserProfileUpdateRequest(
                    name = state.name,
                    status = state.status,
                    about = state.about,
                )
            ).fold(
                onSuccess = { emitEvent(ProfileEditEvent.OnBack) },
                onFailure = { it.printStackTrace() }
            )
        }
    }
}