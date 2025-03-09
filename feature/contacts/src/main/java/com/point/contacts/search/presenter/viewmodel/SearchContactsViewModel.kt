package com.point.contacts.search.presenter.viewmodel

import com.point.contacts.search.data.ContactsRepository
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchContactsViewModel @Inject constructor(private val contactsRepository: ContactsRepository) :
    MviViewModel<SearchContactState, SearchContactActions, Any>(
        SearchContactState()
    ) {

    init {
        onTextInputed()
        onCreateChatWithContact()
    }

    override fun reduce(action: SearchContactActions, state: SearchContactState) = when (action) {
        is SearchContactActions.Event.OnContactLoadSuccessfully -> state.copy(
            contacts = action.contacts.map { Contact(it.id, it.name) },
            isError = false
        )

        SearchContactActions.Event.OnContactLoadFailed -> state.copy(isError = true)

        is SearchContactActions.Action.OnValueChanged -> state.copy(search = action.value)

        is SearchContactActions.Action.AddContact -> state
    }

    private fun onCreateChatWithContact() {
        handleAction<SearchContactActions.Action.AddContact> {
            contactsRepository.addContact(id = it.id)
                .fold(
                    onSuccess = { },
                    onFailure = { it.printStackTrace() }
                )
        }
    }

    private fun onTextInputed() {
        handleAction<SearchContactActions.Action.OnValueChanged> {
            contactsRepository.getContactsByName(name = it.value)
                .fold(
                    onSuccess = { contacts ->
                        if (contacts != null) {
                            emitAction(SearchContactActions.Event.OnContactLoadSuccessfully(contacts))
                        }
                    },
                    onFailure = { emitAction(SearchContactActions.Event.OnContactLoadFailed) }
                )
        }
    }

}