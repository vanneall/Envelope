package ru.point.user.repository

import android.net.Uri
import ru.point.user.models.DetailedUserProfile
import ru.point.user.models.RequestsInfo
import ru.point.user.models.SearchUsersResult
import ru.point.user.models.UserContact
import ru.point.user.models.UserInfo
import ru.point.user.models.UserLightInfo
import ru.point.user.models.UserProfileUpdate

interface UserRepository {

    suspend fun fetchUserContacts(offset: Int = 0, limit: Int = 35): Result<List<UserContact>>

    suspend fun deleteFromUserContacts(username: String): Result<Unit>

    suspend fun fetchUserDetailedInfo(): Result<DetailedUserProfile>

    suspend fun updateUserInfo(data: UserProfileUpdate, photo: Uri?): Result<Unit>

    suspend fun fetchUsersByName(name: String, offset: Int = 0, limit: Int = 35): Result<SearchUsersResult>

    suspend fun fetchIncomingRequests(offset: Int = 0, limit: Int = 35): Result<List<RequestsInfo>>

    suspend fun acceptRequest(id: Long): Result<Unit>

    suspend fun denyRequest(id: Long): Result<Unit>

    suspend fun sendRequest(username: String): Result<Unit>

    suspend fun getUserInfo(username: String): Result<UserInfo>

    suspend fun getLightInfo(ids: List<String>): Result<List<UserLightInfo>>
}