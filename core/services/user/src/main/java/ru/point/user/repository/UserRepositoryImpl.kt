package ru.point.user.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import ru.point.user.models.ApplyRequestAbility
import ru.point.user.models.UserProfileUpdate
import ru.point.user.models.toModel
import ru.point.user.models.toRequest
import ru.point.user.requests.AddToContactRequest
import ru.point.user.requests.ApplyRequestRequest
import ru.point.user.services.UserService
import java.io.File

internal class UserRepositoryImpl(
    private val context: Context,
    private val userService: UserService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {

    override suspend fun fetchUserContacts(offset: Int, limit: Int) = withContext(dispatcher) {
        require(offset >= 0) { "Offset must be greater or equals 0" }
        require(limit >= 0) { "Limit must be greater or equals 0" }

        userService.fetchUserContacts(offset = offset, limit = limit).map { response ->
            response.map { contact -> contact.toModel() }
        }
    }

    override suspend fun deleteFromUserContacts(username: String) = withContext(dispatcher) {
        require(username.isNotEmpty()) { "Username must not be empty" }

        userService.deleteContactById(username = username)
    }

    override suspend fun fetchUserDetailedInfo() = withContext(dispatcher) {
        userService.fetchUserDetailedInfo().map { response -> response.toModel() }
    }

    override suspend fun updateUserInfo(data: UserProfileUpdate, photo: Uri?) = withContext(dispatcher) {
        userService.patchUser(data = data.toRequest(), photo = photo?.let { uriToMultipart(it, context) })
    }

    private fun uriToMultipart(uri: Uri, context: Context): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val fileName = getFileName(uri, contentResolver) ?: "image.jpg"
        val tempFile = File.createTempFile("upload_", fileName, context.cacheDir).apply {
            outputStream().use { fileOut -> inputStream.copyTo(fileOut) }
        }

        val requestBody = tempFile.asRequestBody(contentResolver.getType(uri)!!.toMediaTypeOrNull())

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

    override suspend fun fetchUsersByName(name: String, offset: Int, limit: Int) = withContext(dispatcher) {
        require(offset >= 0) { "Offset must be greater or equals 0" }
        require(limit >= 0) { "Limit must be greater or equals 0" }

        userService.fetchUsersInfoByQuery(name = name, offset = offset, limit = limit).map { response ->
            response.toModel()
        }
    }

    override suspend fun fetchIncomingRequests(offset: Int, limit: Int) = withContext(dispatcher) {
        require(offset >= 0) { "Offset must be greater or equals 0" }
        require(limit >= 0) { "Limit must be greater or equals 0" }

        userService.fetchIncomingRequests(offset = offset, limit = limit).map { response ->
            response.map { request -> request.toModel() }
        }
    }

    override suspend fun fetchOutgoingRequests(offset: Int, limit: Int) = withContext(dispatcher) {
        require(offset >= 0) { "Offset must be greater or equals 0" }
        require(limit >= 0) { "Limit must be greater or equals 0" }

        userService.fetchOutgoingRequests(offset = offset, limit = limit).map { response ->
            response.map { request -> request.toModel() }
        }
    }

    override suspend fun acceptRequest(id: Long) = withContext(dispatcher) {
        userService.patchRequestStatus(
            id = id,
            applyRequestRequest = ApplyRequestRequest(
                result = ApplyRequestAbility.ACCEPT,
            ),
        )
    }

    override suspend fun denyRequest(id: Long) = withContext(dispatcher) {
        userService.patchRequestStatus(
            id = id,
            applyRequestRequest = ApplyRequestRequest(
                result = ApplyRequestAbility.REJECT,
            ),
        )
    }

    override suspend fun cancelRequest(id: Long) = withContext(dispatcher) {
        userService.deleteRequestById(id = id)
    }

    override suspend fun sendRequest(username: String) = withContext(dispatcher) {
        require(username.isNotEmpty()) { "Username must not be empty" }

        userService.postRequest(addToContactRequest = AddToContactRequest(username = username)).map { }
    }

    override suspend fun getUserInfo(username: String) = withContext(dispatcher) {
        require(username.isNotEmpty()) { "Username must not be empty" }

        userService.fetchUserInfoById(username = username).map { response -> response.toModel() }
    }

    override suspend fun getLightInfo(ids: List<String>) = withContext(dispatcher) {
        require(ids.isNotEmpty()) { "Username must not be empty" }

        userService.fetchUsersLightInfoByIds(ids = ids).map { response -> response.map { it.toModel() } }
    }
}