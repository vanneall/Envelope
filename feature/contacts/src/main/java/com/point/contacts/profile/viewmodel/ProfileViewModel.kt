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
) : MviViewModel<ProfileState, ProfileAction, ProfileEvent>(
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
        deleteContact()
        addContact()
        onNavigateToChat()
    }

    override fun reduce(action: ProfileAction, state: ProfileState) = when (action) {
        ProfileAction.Refresh -> state.copy(isRefreshing = true, isRefreshingEnable = false)

        is ProfileAction.ProfileDataLoaded -> state.copy(
            username = action.data.username,
            name = action.data.name,
            status = action.data.status,
            about = action.data.about,
            photos = action.data.photos,
            isRefreshing = false,
            isInitialLoading = false,
            isRefreshingEnable = true,
            userInContacts = action.data.inContacts,
            userInSent = action.data.inSentRequests,
        )

        ProfileAction.UserDeletedSuccessfully -> state.copy(
            userInContacts = false,
        )

        ProfileAction.SentRequestSuccessfully -> state.copy(
            userInSent = true,
        )

        ProfileAction.ToChat,
        is ProfileAction.DeleteFromContacts,
        is ProfileAction.AddContact -> state
    }

    private fun handleRefreshing() {
        handleAction<ProfileAction.Refresh> {
            contactsRepository.fetchUserDataShort(username).fold(
                onSuccess = { emitAction(ProfileAction.ProfileDataLoaded(it)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun deleteContact() {
        handleAction<ProfileAction.DeleteFromContacts> {
            contactsRepository.deleteUserFromFriends(username).fold(
                onSuccess = {
                    emitAction(ProfileAction.UserDeletedSuccessfully)
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun addContact() {
        handleAction<ProfileAction.AddContact> {
            contactsRepository.sendRequest(username).fold(
                onSuccess = {
                    emitAction(ProfileAction.SentRequestSuccessfully)
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun onNavigateToChat() {
        handleAction<ProfileAction.ToChat> {
            contactsRepository.getChatId(username).fold(
                onSuccess = { emitEvent(ProfileEvent.NavigateToChat(id = username)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("username") username: String): ProfileViewModel
    }
}