package com.point.chats.dialog.viewmodel

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.point.chats.dialog.data.ChatDialogRepository
import com.point.chats.dialog.data.MediaContentRepository
import com.point.chats.dialog.data.events.DeleteMessageEvent
import com.point.chats.dialog.data.events.MessageEditedEvent
import com.point.chats.dialog.data.events.MessageSentEvent
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
    private val chatId: String,
    private val chatDialogRepository: ChatDialogRepository,
    private val mediaContentRepository: MediaContentRepository,
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
        }

        onSendMessage()
        onDeleteMessage()
        onEditMessage()
    }

    override fun reduce(action: ChatDialogAction, state: ChatDialogState) = when (action) {
        is ChatDialogAction.TypeMessage -> state.copy(message = action.value)
        ChatDialogAction.Send -> state
        is ChatDialogAction.UpdateList -> {
            if (action.text is MessageSentEvent) {
                Timber.tag("mong").d("${action.text.attachments}")
            }

            state.copy(
                events = listOf(action.text) + state.events,
                photos = emptyList(),
            )
        }

        is ChatDialogAction.UiAction.OnPhotoPicked -> state.copy(photos = action.photos)

        is ChatDialogAction.EventsLoaded -> {
            state.copy(
                events = action.list,
                isInitialLoading = false,
            )
        }

        is ChatDialogAction.UiAction.OnPhotoDeletedFromMessage -> state.copy(photos = state.photos.filter { uri -> uri != action.uri })

        is ChatDialogAction.Edit -> state.copy(
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
                if (it.id == action.id && it is MessageSentEvent) {
                    it.copy(text = action.text, isEdited = true)
                } else {
                    it
                }
            }
        )

        is ChatDialogAction.DeleteSuccess -> state.copy(events = state.events.filter { it.id != action.id })
        ChatDialogAction.ClearField -> state.copy(message = "")
        is ChatDialogAction.Delete -> state
    }

    private suspend fun onConnectToChat() {
        chatDialogRepository.connectToChat(chatId = chatId).collect { event ->
            when (event) {
                is DeleteMessageEvent -> emitAction(ChatDialogAction.DeleteSuccess(event.messageId))
                is MessageEditedEvent -> emitAction(
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
        handleAction<ChatDialogAction.Send> {
            val state = state
            if (state.isInEditMode != null) {
                if (state.message.isNotEmpty() && state.message != state.isInEditMode.text) {
                    chatDialogRepository.editMessage(state.isInEditMode.messageId, state.message)
                }
            } else {
                sendMessage(message = state.message, state.photos)
            }
        }
    }

    private suspend fun sendMessage(message: String, photos: List<Uri>) {

        val photoIds = photos.mapNotNull { uri ->
            mediaContentRepository.uploadPhoto(uri).onFailure { it.printStackTrace() }.getOrThrow().id
        }
        chatDialogRepository.sendMessage(text = message, photoIds = photoIds)
    }

    private fun onDeleteMessage() {
        handleAction<ChatDialogAction.Delete> {
            chatDialogRepository.deleteMessage(id = it.id)
        }
    }

    private fun onEditMessage() {
        handleAction<ChatDialogAction.Edit> { action ->
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