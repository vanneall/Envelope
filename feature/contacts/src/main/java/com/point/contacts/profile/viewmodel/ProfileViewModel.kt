package com.point.contacts.profile.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.services.chats.models.ChatCreationData
import com.point.services.chats.repository.ChatsRepository
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber

@HiltViewModel(assistedFactory = ProfileViewModel.Factory::class)
class ProfileViewModel @AssistedInject constructor(
    @Assisted("username")
    private val username: String,
    private val contactsRepository: UserRepository,
    private val chatsRepository: ChatsRepository,
) : MviViewModel<ProfileState, ProfileAction, ProfileEvent>(
    initialValue = ProfileState()
) {

    init {
        viewModelScope.launch {
            contactsRepository.getUserInfo(username).fold(
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
            photos = listOf(
                "https://static1.dualshockersimages.com/wordpress/wp-content/uploads/2018/09/DMC5_Screens_Dante-Intro03_result.png",
                "https://cs14.pikabu.ru/post_img/big/2022/09/24/11/1664049510190058921.jpg",
            ),
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
            contactsRepository.getUserInfo(username).fold(
                onSuccess = { emitAction(ProfileAction.ProfileDataLoaded(it)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun deleteContact() {
        handleAction<ProfileAction.DeleteFromContacts> {
            contactsRepository.deleteFromUserContacts(username).fold(
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
            chatsRepository.createChat(
                data = ChatCreationData(
                    name = null,
                    participants = listOf(username),
                )
            ).fold(
                onSuccess = { emitEvent(ProfileEvent.NavigateToChat(it)) },
                onFailure = { Timber.tag("Error").e(it.stackTraceToString()) }
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("username") username: String): ProfileViewModel
    }
}