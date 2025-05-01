package com.point.services.chats.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.point.services.chats.models.ChatCreationData
import com.point.services.chats.models.GroupChatInfo
import com.point.services.chats.models.toModel
import com.point.services.chats.requests.DeleteChatsRequest
import com.point.services.chats.requests.toRequest
import com.point.services.chats.services.ChatsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

internal class ChatsRepositoryImpl(
    private val context: Context,
    private val chatsService: ChatsService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ChatsRepository {

    override suspend fun createChat(data: ChatCreationData, uri: Uri?) = withContext(dispatcher) {
        chatsService.createChat(data = data.toRequest(), uri?.let { uriToMultipart(it, context) })
            .map { response -> response.id }
    }

    override suspend fun getGroupChatInfo(chatId: String): Result<GroupChatInfo> = withContext(dispatcher) {
        chatsService.getGroupChatInfo(chatId = chatId).map { response -> response.toModel() }
    }

    override suspend fun deleteUserFromChat(username: String, chatId: String) = withContext(dispatcher) {
        chatsService.deleteUserFromChat(chatId = chatId, username = username)
    }

    override suspend fun getChats(offset: Int, limit: Int) = withContext(dispatcher) {
        chatsService.getChats(offset = offset, limit = limit).map { response ->
            response.map { chats -> chats.toModel() }
        }
    }

    override suspend fun delete(id: String) = withContext(dispatcher) {
        chatsService.delete(id = id)
    }

    override suspend fun delete(ids: List<String>) = withContext(dispatcher) {
        chatsService.delete(deleteChatsRequest = DeleteChatsRequest(chatIds = ids))
    }

    override suspend fun getChatEvents(chatId: String) = withContext(dispatcher) {
        chatsService.getChatEvents(id = chatId).map { response ->
            response.map { event -> event.toModel() }
        }
    }

    private fun uriToMultipart(uri: Uri, context: Context): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val fileName = getFileName(uri, contentResolver) ?: "image.jpg"
        val tempFile = File.createTempFile("upload_", fileName, context.cacheDir).apply {
            outputStream().use { fileOut -> inputStream.copyTo(fileOut) }
        }

        val requestBody = tempFile.asRequestBody("image/*".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("photo", fileName, requestBody)
    }

    private fun getFileName(uri: Uri, contentResolver: ContentResolver): String? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        }
    }
}