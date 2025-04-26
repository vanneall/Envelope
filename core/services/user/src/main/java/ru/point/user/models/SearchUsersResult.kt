package ru.point.user.models

import ru.point.user.responses.SearchUsersResponse

data class SearchUsersResult(
    val inContacts: List<OtherUser>,
    val allContacts: List<OtherUser>,
)

internal fun SearchUsersResponse.toModel() = SearchUsersResult(
    inContacts = inContacts.map { user -> user.toModel() },
    allContacts = allContacts.map { user -> user.toModel() },
)
