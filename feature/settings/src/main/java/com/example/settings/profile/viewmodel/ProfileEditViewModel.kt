package com.example.settings.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val contactsRepository: UserRepository
) : MviViewModel<ProfileEditState, ProfileEditAction, ProfileEditEvent>(
    initialValue = ProfileEditState()
) {

    init {
        viewModelScope.launch {
//            contactsRepository.getUserInfo()
//            contactsRepository.fetchUserData().fold(
//                onSuccess = {
//                    emitAction(ProfileEditAction.OnDataFetchedSuccess(it))
//                },
//                onFailure = { it.printStackTrace() }
//            )
        }

        handleOnSave()
    }

    override fun reduce(action: ProfileEditAction, state: ProfileEditState) = when (action) {

        is ProfileEditAction.OnDataFetchedSuccess -> state.copy(
            name = action.data.name,
            status = action.data.status.orEmpty(),
            about = action.data.about.orEmpty(),
            initialPhotoUrl = action.data.photos.firstOrNull()
        )

        is ProfileEditAction.OnNameEntered -> state.copy(name = action.value)
        is ProfileEditAction.OnStatusEntered -> state.copy(status = action.value)
        is ProfileEditAction.OnAboutEntered -> state.copy(about = action.value)

        is ProfileEditAction.PickPhoto -> state.copy(photoUri = action.uri)

        ProfileEditAction.OnSavePressed,
        ProfileEditAction.OnSaveSuccessfully -> state
    }

    private fun handleOnSave() {
        handleAction<ProfileEditAction.OnSavePressed> {
//            if (state.name.isEmpty()) return@handleAction
//
//            contactsRepository.saveUserData(
//                UserProfileUpdateRequest(
//                    name = state.name,
//                    status = state.status,
//                    about = state.about,
//                ),
//                photoUri = state.photoUri
//            ).fold(
//                onSuccess = { emitEvent(ProfileEditEvent.OnBack) },
//                onFailure = { it.printStackTrace() }
//            )
        }
    }
}