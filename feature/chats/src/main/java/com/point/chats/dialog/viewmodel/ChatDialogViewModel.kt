package com.point.chats.dialog.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.point.chats.dialog.data.ChatDialogRepository
import com.point.chats.dialog.viewmodel.ChatDialogAction.UiEvent
import com.point.services.chats.events.models.MessageDeleted
import com.point.services.chats.events.models.MessageEdited
import com.point.services.chats.events.models.MessageUi
import com.point.services.media.repository.MediaRepository
import com.point.viewmodel.MviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel(assistedFactory = ChatDialogViewModel.Factory::class)
class ChatDialogViewModel @AssistedInject constructor(
    @Assisted(FACTORY_CHAT_ID)
    val chatId: String,
    private val chatDialogRepository: ChatDialogRepository,
    private val mediaRepository: MediaRepository,
) : MviViewModel<ChatDialogState, ChatDialogAction, ChatDialogEvent>(
    initialValue = ChatDialogState()
) {

    init {
        viewModelScope.launch {
            launch { onConnectToChat() }
            launch {
                chatDialogRepository.fetchInfo(chatId).fold(
                    onSuccess = {
                        emitAction(ChatDialogAction.EventsLoaded(it))
                    },
                    onFailure = { it.printStackTrace() }
                )
            }
            launch {
                chatDialogRepository.fetchChatInfo(chatId).fold(
                    onSuccess = {
                        emitAction(ChatDialogAction.SetChatType(it.type))
                        emitEvent(ChatDialogEvent.ChatInited(it.name, it.photo))
                    },
                    onFailure = {
                        it.printStackTrace()
                    },
                )
            }
        }

        onSendMessage()
        onDeleteMessage()
        onEditMessage()
    }

    override fun reduce(action: ChatDialogAction, state: ChatDialogState) = when (action) {
        is UiEvent.TypeMessage -> state.copy(message = action.value)
        UiEvent.SendMessage -> state
        is ChatDialogAction.UpdateList -> {
            if (action.event is MessageUi) {
                Timber.tag("mong").d("${action.event.attachments}")
            }

            state.copy(
                events = listOf(action.event) + state.events,
                photos = emptyList(),
            )
        }

        is UiEvent.OnPhotoPicked -> state.copy(photos = action.photos)

        is ChatDialogAction.EventsLoaded -> {
            state.copy(
                events = action.list,
                isInitialLoading = false,
            )
        }

        is UiEvent.OnPhotoDeletedFromMessage -> state.copy(photos = state.photos.filter { uri -> uri != action.uri })

        is UiEvent.EditMessage -> state.copy(
            message = action.text,
            isInEditMode = EditMode(
                messageId = action.id,
                text = action.text,
            )
        )

        is ChatDialogAction.EditSuccess -> state.copy(
            message = "",
            isInEditMode = null,
            events = state.events.map {
                if (it.id == action.id && it is MessageUi) {
                    it.copy(text = action.text, isEdited = true)
                } else {
                    it
                }
            }
        )

        is ChatDialogAction.SetChatType -> state.copy(chatType = action.chatType)

        is ChatDialogAction.DeleteSuccess -> state.copy(events = state.events.filter { it.id != action.id })
        ChatDialogAction.ClearField -> state.copy(message = "")
        is UiEvent.DeleteMessage -> state
    }

    private suspend fun onConnectToChat() {
        chatDialogRepository.connectToChat(chatId = chatId).collect { event ->
            when (event) {
                is MessageDeleted -> emitAction(ChatDialogAction.DeleteSuccess(event.messageId))
                is MessageEdited -> emitAction(
                    ChatDialogAction.EditSuccess(
                        event.editedMessageId,
                        text = event.newContent
                    )
                )

                else -> {
                    emitAction(ChatDialogAction.UpdateList(event))
                    emitAction(ChatDialogAction.ClearField)
                }
            }
        }
    }

    private fun onSendMessage() {
        handleAction<UiEvent.SendMessage> {
            val state = state
            if (state.isInEditMode != null) {
                if (state.message.isNotEmpty() && state.message != state.isInEditMode.text) {
                    chatDialogRepository.editMessage(state.isInEditMode.messageId, state.message)
                }
            } else {
                if (state.message.isNotEmpty() || state.photos.isNotEmpty()) sendMessage(
                    message = state.message,
                    state.photos
                )
            }
        }
    }

    private suspend fun sendMessage(message: String, photos: List<Uri>) {

        val photoIds = photos.map { uri ->
            mediaRepository.uploadPhoto(uri).onFailure { it.printStackTrace() }.getOrThrow()
        }
        chatDialogRepository.sendMessage(text = message, photoIds = photoIds)
    }

    private fun onDeleteMessage() {
        handleAction<UiEvent.DeleteMessage> {
            chatDialogRepository.deleteMessage(id = it.id)
        }
    }

    private fun onEditMessage() {
        handleAction<UiEvent.EditMessage> { action ->
            emitEvent(ChatDialogEvent.OnEditMessage(action.text))
        }
    }

    override fun onCleared() {
        super.onCleared()
        chatDialogRepository.disconnect()
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(FACTORY_CHAT_ID) chatId: String): ChatDialogViewModel
    }

    private companion object {
        const val FACTORY_CHAT_ID = "chatId"
    }
}