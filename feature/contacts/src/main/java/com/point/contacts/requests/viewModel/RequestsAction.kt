package com.point.contacts.requests.viewModel

import ru.point.user.models.RequestsInfo

sealed interface RequestsAction {

    data class LoadUserContacts(val contacts: List<RequestsInfo>) : RequestsAction

    data class AcceptRequest(val userId: String) : RequestsAction

    data class DenyRequest(val userId: String) : RequestsAction

    data class RequestHandledSuccessfully(val userId: String) : RequestsAction

    data object Refresh : RequestsAction

}