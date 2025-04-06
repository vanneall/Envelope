package com.point.contacts.search.viewModel

import com.point.contacts.data.ContactsRepository
import com.point.contacts.main.presenter.viewmodel.Contact
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : MviViewModel<SearchContactsState, SearchContactsAction, Any>(
    initialValue = SearchContactsState()
) {

    init {
        handleOnNameTyped()
        handleSendRequest()
    }

    override fun reduce(action: SearchContactsAction, state: SearchContactsState) = when (action) {
        is SearchContactsAction.OnNameTyped -> state.copy(query = action.query)

        is SearchContactsAction.LoadUserContacts -> state.copy(
            inContacts = action.contacts.inContacts.map {
                Contact(
                    username = it.username,
                    name = it.name,
                    status = it.status.orEmpty(),
                    inContacts = it.inContacts,
                    isSentRequest = it.inSentRequests,
                )
            },
            allContacts = action.contacts.allContacts.map {
                Contact(
                    username = it.username,
                    name = it.name,
                    status = it.status.orEmpty(),
                    inContacts = it.inContacts,
                    isSentRequest = it.inSentRequests,
                    photoUrl = it.photo?.let { uri -> "http://192.168.0.192:8084/photos/$uri" }
                )
            },
        )

        is SearchContactsAction.SendRequest -> state
    }

    private fun handleOnNameTyped() {
        handleAction<SearchContactsAction.OnNameTyped> { action ->
            contactsRepository.fetchUsersByName(query = action.query).fold(
                onSuccess = {
                    Timber.tag(TAG).i("fetch success")
                    emitAction(SearchContactsAction.LoadUserContacts(it))
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun handleSendRequest() {
        handleAction<SearchContactsAction.SendRequest> { action ->
            contactsRepository.sendRequest(action.userId).fold(
                onSuccess = { Timber.tag(TAG).i("send request success") },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    companion object {
        private const val TAG = "SearchContactsViewModel"
    }
}