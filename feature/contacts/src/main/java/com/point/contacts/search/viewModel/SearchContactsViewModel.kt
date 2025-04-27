package com.point.contacts.search.viewModel

import com.point.contacts.search.data.ContactState
import com.point.contacts.search.data.toContactUserUi
import com.point.contacts.search.viewModel.UserSearchAction.ModelAction
import com.point.contacts.search.viewModel.UserSearchAction.UiAction
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.point.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
internal class SearchContactsViewModel @Inject constructor(
    private val contactsRepository: UserRepository,
) : MviViewModel<SearchContactsState, UserSearchAction, Any>(initialValue = SearchContactsState()) {

    init {
        handleOnNameTyped()
        handleSendRequest()
        handleRefresh()
    }

    override fun reduce(action: UserSearchAction, state: SearchContactsState) = when (action) {
        is UiAction.OnNameTyped -> state.copy(name = action.name)

        UiAction.Refresh -> state.copy(isRefreshing = true)

        is ModelAction.LoadUserUser -> state.copy(
            inContacts = action.contacts.inContacts.map { contact -> contact.toContactUserUi() },
            globalContacts = action.contacts.allContacts.map { contact -> contact.toContactUserUi() },
        )

        is ModelAction.RequestToAddSentSuccessfully -> state.copy(
            globalContacts = state.globalContacts.map { user ->
                if (user.username == action.username) user.copy(
                    contactState = ContactState.REQUEST_SENT
                ) else {
                    user
                }
            },
        )

        is ModelAction.StopLoading -> state.copy(isRefreshing = false)

        is UiAction.SendRequest -> state
    }

    private fun handleOnNameTyped() {
        handleAction<UiAction.OnNameTyped> { action ->
            getContactsByName(name = action.name)
        }
    }

    private suspend fun getContactsByName(name: String) {
        contactsRepository.fetchUsersByName(name = name).fold(
            onSuccess = { emitAction(ModelAction.LoadUserUser(it)) },
            onFailure = { it.printStackTrace() }
        )
        emitAction(ModelAction.StopLoading)
    }

    private fun handleSendRequest() {
        handleAction<UiAction.SendRequest> { action ->
            contactsRepository.sendRequest(action.username).fold(
                onSuccess = { emitAction(ModelAction.RequestToAddSentSuccessfully(action.username)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun handleRefresh() {
        handleAction<UiAction.Refresh> {
            getContactsByName(name = state.name)
        }
    }

    companion object {
        private const val TAG = "SearchContactsViewModel"
    }
}