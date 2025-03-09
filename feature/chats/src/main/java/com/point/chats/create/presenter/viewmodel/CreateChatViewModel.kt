package com.point.chats.create.presenter.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.point.chats.create.repository.ContactsRepository
import com.point.viewmodel.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateChatViewModel @Inject constructor(private val contactsRepository: ContactsRepository) :
    MviViewModel<CreateChatState, CreateChatAction, CreateChatEvent>(
        CreateChatState()
    ) {

    init {
        viewModelScope.launch {
            contactsRepository.fetchContacts()
                .fold(
                    onSuccess = { emitAction(CreateChatAction.Event.OnContactLoadSuccessfully(it)) },
                    onFailure = { emitAction(CreateChatAction.Event.OnContactLoadFailed) }
                )
        }

        onTextInputed()
        onCreateChatWithContact()
    }

    override fun reduce(action: CreateChatAction, state: CreateChatState) = when (action) {
        is CreateChatAction.Event.OnContactLoadSuccessfully -> state.copy(
            contacts = action.contacts.map { Contact(it.id, it.name) },
            isError = false
        )

        CreateChatAction.Event.OnContactLoadFailed -> state.copy(isError = true)

        is CreateChatAction.Action.OnValueChanged -> state.copy(search = action.value)

        is CreateChatAction.Action.CreateChatWithContact -> state
    }

    private fun onCreateChatWithContact() {
        handleAction<CreateChatAction.Action.CreateChatWithContact> {
            contactsRepository.createChatWithContact(id = it.id)
                .fold(
                    onSuccess = { emitEvent(CreateChatEvent.ChatCreatedSuccessfully) },
                    onFailure = { it.printStackTrace() }
                )
        }
    }

    private fun onTextInputed() {
        mapAction<CreateChatAction.Action.OnValueChanged> {
            contactsRepository.fetchContacts(name = it.value)
                .fold(
                    onSuccess = { contacts ->
                        CreateChatAction.Event.OnContactLoadSuccessfully(contacts)
                    },
                    onFailure = { CreateChatAction.Event.OnContactLoadFailed }
                )
        }
    }

}