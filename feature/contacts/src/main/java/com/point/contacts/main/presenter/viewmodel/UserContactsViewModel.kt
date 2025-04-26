package com.point.contacts.main.presenter.viewmodel

import androidx.lifecycle.viewModelScope
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserContactsViewModel @Inject constructor(private val contactsRepository: UserRepository) :
    MviViewModel<ContactState, ContactsActions, Any>(
        ContactState()
    ) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchUserContacts()
                .fold(
                    onSuccess = {
                        delay(1500)
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
            contacts = action.contacts.groupBy(
                keySelector = { it.name.first() },
                valueTransform = {
                    Contact(
                        username = it.username,
                        name = it.name,
                        status = it.status.orEmpty(),
                        photoUrl = it.lastPhoto
                    )
                }
            ),
            isRefreshing = false,
            isRefreshingEnabled = true,
            isInitialLoading = false,
        )

        ContactsActions.Refresh -> state.copy(
            isRefreshing = true,
            isRefreshingEnabled = false,
        )
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