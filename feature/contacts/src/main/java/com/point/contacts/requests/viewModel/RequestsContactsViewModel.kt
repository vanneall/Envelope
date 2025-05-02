package com.point.contacts.requests.viewModel

import androidx.lifecycle.viewModelScope
import com.point.contacts.requests.viewModel.RequestsAction.ModelAction
import com.point.contacts.requests.viewModel.RequestsAction.UiAction
import com.point.ui.components.user.UserBase
import com.point.ui.components.user.UserCardButtonInfo
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.point.user.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
internal class RequestsContactsViewModel @Inject constructor(
    private val contactsRepository: UserRepository,
) : MviViewModel<RequestsState, RequestsAction, Any>(
    initialValue = RequestsState(),
) {

    init {
        viewModelScope.launch { getUserRequests() }
        handleRefresh()
        denyRequest()
        acceptRequest()
        getUserOutgoingRequests()
        handleDelete()
    }

    override fun reduce(action: RequestsAction, state: RequestsState) = when (action) {

        is UiAction.Refresh -> state.copy(isRefreshing = true, isRefreshingEnabled = true)

        is ModelAction.RequestsLoaded -> state.copy(
            contacts = action.contacts.map {
                RequestUi(
                    id = it.id,
                    username = it.username,
                    userBase = UserCardButtonInfo(
                        userBase = UserBase(
                            name = it.name,
                            photo = it.lastPhoto,
                        ),
                    )
                )
            },
            isRefreshing = false,
            isRefreshingEnabled = true,
            isInitialLoading = false,
        )

        is ModelAction.RequestHandledSuccessfully -> state.copy(
            contacts = state.contacts.filter { it.id != action.id }
        )

        is UiAction.DenyRequest,
        is UiAction.AcceptRequest -> state

        is UiAction.Select -> state.copy(selected = action.i)

        is UiAction.Cancel -> state
        is ModelAction.RequestDeletedSuccessfully -> state.copy(contacts = state.contacts.filter { it.id != action.id })
    }

    private fun handleRefresh() {
        handleAction<UiAction.Refresh> {
            getUserRequests()
        }
    }

    private fun handleDelete() {
        handleAction<UiAction.Cancel> { act ->
            contactsRepository.cancelRequest(act.id).fold(
                onSuccess = { emitAction(ModelAction.RequestDeletedSuccessfully(id = act.id)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private suspend fun getUserRequests() {
        contactsRepository.fetchIncomingRequests().fold(
            onSuccess = { emitAction(ModelAction.RequestsLoaded(it)) },
            onFailure = { it.printStackTrace() },
        )
    }

    private suspend fun getUserRequests2() {
        contactsRepository.fetchOutgoingRequests().fold(
            onSuccess = { emitAction(ModelAction.RequestsLoaded(it)) },
            onFailure = { it.printStackTrace() },
        )
    }

    private fun getUserOutgoingRequests() {
        handleAction<UiAction.Select> {
            if (it.i == 0) {
                getUserRequests()
            } else {
                getUserRequests2()
            }
        }
    }

    private fun acceptRequest() {
        handleAction<UiAction.AcceptRequest> { action ->
            contactsRepository.acceptRequest(action.id).fold(
                onSuccess = { emitAction(ModelAction.RequestHandledSuccessfully(action.id)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    private fun denyRequest() {
        handleAction<UiAction.DenyRequest> { action ->
            contactsRepository.denyRequest(action.id).fold(
                onSuccess = { emitAction(ModelAction.RequestHandledSuccessfully(action.id)) },
                onFailure = { it.printStackTrace() }
            )
        }
    }

    companion object {
        private const val TAG = "SearchContactsViewModel"
    }
}