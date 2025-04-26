package com.point.contacts.requests.viewModel

import ru.point.user.responses.UserInfoShort

sealed interface RequestsAction {

    data class LoadUserContacts(val contacts: List<UserInfoShort>) : RequestsAction

    data class AcceptRequest(val userId: String) : RequestsAction

    data class DenyRequest(val userId: String) : RequestsAction

    data class RequestHandledSuccessfully(val userId: String) : RequestsAction

    data object Refresh : RequestsAction

}