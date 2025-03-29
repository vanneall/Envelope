package com.point.contacts.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.contacts.data.ContactsRepository
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted("username")
    private val username: String,
    private val contactsRepository: ContactsRepository,
) : MviViewModel<ProfileState, ProfileAction, Any>(
    initialValue = ProfileState()
) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchUserDataShort(username).fold(
                onSuccess = {
                    emitAction(ProfileAction.ProfileDataLoaded(it))
                },
                onFailure = { it.printStackTrace() }
            )
        }

        handleRefreshing()
    }

    override fun reduce(action: ProfileAction, state: ProfileState) = when(action) {
        ProfileAction.Refresh -> state.copy(isRefreshing = true, isRefreshingEnable = false)

        is ProfileAction.ProfileDataLoaded -> state.copy(
            username = action.data.username,
            name = action.data.name,
            status = action.data.status,
            about = action.data.about,
            isRefreshing = false,
            isInitialLoading = false,
            isRefreshingEnable = true,
            userInContacts = action.data.inContacts,
            userInSent = action.data.inSentRequests,
        )
    }

    private fun handleRefreshing() {
        handleAction<ProfileAction.Refresh> {
            contactsRepository.fetchUserDataShort(username).fold(
                onSuccess = { emitAction(ProfileAction.ProfileDataLoaded(it)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("username") username: String): ProfileViewModel
    }
}