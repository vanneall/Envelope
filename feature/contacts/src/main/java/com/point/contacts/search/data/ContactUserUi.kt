package com.point.contacts.search.data

import com.point.ui.components.user.UserBase
import com.point.ui.components.user.UserCardInfo
import ru.point.user.models.OtherUser

internal data class ContactUserUi(
    val username: String,
    val user: UserCardInfo,
    val contactState: ContactState,
)


internal fun OtherUser.toContactUserUi() = ContactUserUi(
    username = username,
    user = UserCardInfo(
        userBase = UserBase(name = name, photo = photo),
        supportText = status,
    ),
    contactState = when {
        inContacts -> ContactState.IN_CONTACTS
        inSentRequests -> ContactState.REQUEST_SENT
        else -> ContactState.NOT_IN_CONTACTS
    }
)
