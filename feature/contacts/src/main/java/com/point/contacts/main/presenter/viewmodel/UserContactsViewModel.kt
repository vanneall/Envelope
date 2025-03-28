package com.point.contacts.main.presenter.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.contacts.data.ContactsRepository
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserContactsViewModel @Inject constructor(private val contactsRepository: ContactsRepository) :
    MviViewModel<ContactState, ContactsActions, Any>(
        ContactState()
    ) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchUserContacts()
                .fold(
                    onSuccess = {
                        Timber.tag(TAG).i("fetch success")
                        emitAction(ContactsActions.LoadUserContacts(it))
                    },
                    onFailure = { it.printStackTrace() }
                )
        }

        handleRefresh()
    }

    override fun reduce(action: ContactsActions, state: ContactState) = when (action) {
        is ContactsActions.LoadUserContacts -> state.copy(
            contacts = action.contacts.map {
                Contact(
                    id = it.username,
                    name = it.name,
                    status = it.status.orEmpty(),
                )
            },
            isRefreshing = false,
        )
        ContactsActions.Refresh -> state.copy(isRefreshing = true)
    }

    private fun handleRefresh() {
        handleAction<ContactsActions.Refresh> {
            delay(1500)
            contactsRepository.fetchUserContacts()
                .fold(
                    onSuccess = {
                        Timber.tag(TAG).i("fetch success")
                        emitAction(ContactsActions.LoadUserContacts(it))
                    },
                    onFailure = { it.printStackTrace() },
                )
        }
    }

    companion object {
        private const val TAG = "UserContactsViewModel"
    }
}