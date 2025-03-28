package com.point.contacts.requests.viewModel

import androidx.lifecycle.viewModelScope
import com.point.contacts.data.ContactsRepository
import com.point.contacts.main.presenter.viewmodel.Contact
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RequestsContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository,
) : MviViewModel<RequestsState, RequestsAction, Any>(
    initialValue = RequestsState(),
) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchIncomingRequests().fold(
                onSuccess = {
                    Timber.tag(TAG).i("fetch success")
                    delay(1500)
                    emitAction(RequestsAction.LoadUserContacts(it))
                },
                onFailure = { it.printStackTrace() },
            )
        }
        handleRefresh()
        denyRequest()
        acceptRequest()
    }

    override fun reduce(action: RequestsAction, state: RequestsState) = when (action) {

        is RequestsAction.Refresh -> state.copy(isRefreshing = true)

        is RequestsAction.LoadUserContacts -> state.copy(
            contacts = action.contacts.map {
                Contact(
                    id = it.username,
                    name = it.name,
                    status = it.status.orEmpty(),
                )
            },
            isRefreshing = false,
        )

        is RequestsAction.RequestHandledSuccessfully -> state.copy(
            contacts = state.contacts.filter { it.id != action.userId }
        )

        is RequestsAction.DenyRequest,
        is RequestsAction.AcceptRequest -> state
    }

    private fun handleRefresh() {
        handleAction<RequestsAction.Refresh> {
            contactsRepository.fetchIncomingRequests()
                .fold(
                    onSuccess = {
                        Timber.tag(TAG).i("fetch success")
                        delay(1500)
                        emitAction(RequestsAction.LoadUserContacts(it))
                    },
                    onFailure = { it.printStackTrace() },
                )
        }
    }

    private fun acceptRequest() {
        handleAction<RequestsAction.AcceptRequest> { action ->
            contactsRepository.acceptRequest(action.userId).fold(
                onSuccess = {
                    Timber.tag(TAG).i("accept success")
                    emitAction(RequestsAction.RequestHandledSuccessfully(action.userId))
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun denyRequest() {
        handleAction<RequestsAction.DenyRequest> { action ->
            contactsRepository.denyRequest(action.userId).fold(
                onSuccess = {
                    Timber.tag(TAG).i("reject success")
                    emitAction(RequestsAction.RequestHandledSuccessfully(action.userId))
                },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    companion object {
        private const val TAG = "SearchContactsViewModel"
    }
}