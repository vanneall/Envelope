package com.point.contacts.search.presenter.viewmodel

import com.point.contacts.search.data.response.UserInfoShort

sealed interface SearchContactActions {

    sealed interface Action : SearchContactActions {

        data class OnValueChanged(val value: String) : Action

        data class AddContact(val id: String) : Action
    }

    sealed interface Event: SearchContactActions {

        data class OnContactLoadSuccessfully(val contacts: List<UserInfoShort>) : Event
        data object OnContactLoadFailed : Event
    }

}