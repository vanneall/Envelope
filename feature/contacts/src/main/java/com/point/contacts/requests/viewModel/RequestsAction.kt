package com.point.contacts.requests.viewModel

import ru.point.user.models.RequestsInfo

internal sealed interface RequestsAction {

    sealed interface UiAction : RequestsAction {

        @JvmInline
        value class AcceptRequest(val id: Long) : UiAction

        @JvmInline
        value class DenyRequest(val id: Long) : UiAction

        data object Refresh : UiAction

        @JvmInline
        value class Select(val i: Int) : UiAction

        @JvmInline
        value class Cancel(val id: Long) : UiAction
    }

    sealed interface ModelAction : RequestsAction {

        data class RequestsLoaded(val contacts: List<RequestsInfo>) : RequestsAction

        @JvmInline
        value class RequestHandledSuccessfully(val id: Long) : ModelAction

        @JvmInline
        value class RequestDeletedSuccessfully(val id: Long) : ModelAction
    }
}