package ru.point.user.repository

import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.point.user.models.ApplyRequestAbility
import ru.point.user.models.UserProfileUpdate
import ru.point.user.models.toModel
import ru.point.user.models.toRequest
import ru.point.user.requests.AddToContactRequest
import ru.point.user.requests.ApplyRequestRequest
import ru.point.user.services.UserService

internal class UserRepositoryImpl(
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
        userService.patchUser(data = data.toRequest(), photo = null)
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

    override suspend fun sendRequest(username: String) = withContext(dispatcher) {
        require(username.isNotEmpty()) { "Username must not be empty" }

        userService.postRequest(addToContactRequest = AddToContactRequest(username = username)).map { }
    }

    override suspend fun getUserInfo(username: String) = withContext(dispatcher) {
        require(username.isNotEmpty()) { "Username must not be empty" }

        userService.fetchUserInfoById(username = username).map { response -> response.toModel() }
    }
}