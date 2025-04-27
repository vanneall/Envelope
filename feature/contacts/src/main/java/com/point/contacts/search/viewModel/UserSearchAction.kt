package com.point.contacts.search.viewModel

import ru.point.user.models.SearchUsersResult

internal sealed interface UserSearchAction {

    sealed interface UiAction : UserSearchAction {

        data class SendRequest(val username: String) : UiAction

        data class OnNameTyped(val name: String) : UiAction

        data object Refresh : UiAction
    }

    sealed interface ModelAction : UserSearchAction {

        data class LoadUserUser(val contacts: SearchUsersResult) : ModelAction

        data class RequestToAddSentSuccessfully(val username: String) : ModelAction

        data object StopLoading : ModelAction
    }
}