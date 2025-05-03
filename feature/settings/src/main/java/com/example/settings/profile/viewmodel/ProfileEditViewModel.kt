package com.example.settings.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.models.UserProfileUpdate
import ru.point.user.repository.UserRepository

@HiltViewModel(assistedFactory = ProfileEditViewModel.Factory::class)
class ProfileEditViewModel @AssistedInject constructor(
    @Assisted("username") username: String,
    private val contactsRepository: UserRepository
) : MviViewModel<ProfileEditState, ProfileEditAction, ProfileEditEvent>(
    initialValue = ProfileEditState()
) {

    init {
        viewModelScope.launch {
            contactsRepository.getUserInfo(username).fold(
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
            initialPhotoUrl = action.data.photos.firstOrNull(),
            date = action.data.birthDate,
        )

        is ProfileEditAction.OnNameEntered -> state.copy(name = action.value)
        is ProfileEditAction.OnStatusEntered -> state.copy(status = action.value)
        is ProfileEditAction.OnAboutEntered -> state.copy(about = action.value)

        is ProfileEditAction.PickPhoto -> state.copy(photoUri = action.uri)
        is ProfileEditAction.PickDate -> state.copy(date = action.localDate)

        ProfileEditAction.OnSavePressed,
        ProfileEditAction.OnSaveSuccessfully -> state
    }

    private fun handleOnSave() {
        handleAction<ProfileEditAction.OnSavePressed> {
            if (state.name.isEmpty()) return@handleAction

            contactsRepository.updateUserInfo(
                UserProfileUpdate(
                    name = state.name,
                    status = state.status,
                    about = state.about,
                    birthDate = state.date,
                ),
                photo = state.photoUri,
            ).fold(
                onSuccess = { emitEvent(ProfileEditEvent.OnBack) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("username") username: String): ProfileEditViewModel
    }
}