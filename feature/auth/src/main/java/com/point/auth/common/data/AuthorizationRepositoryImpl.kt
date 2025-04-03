package com.point.auth.common.data

import com.point.auth.registration.presenter.host.UserRegistrationData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class AuthorizationRepositoryImpl(private val authorizeService: AuthorizeService) :
    AuthorizationRepository {

    override suspend fun tryToAuthorize(authRequest: AuthRequest): Result<TokenPayload> =
        withContext(Dispatchers.IO) {
            authorizeService.authorize(authRequest = authRequest)
        }

    override suspend fun registration(dto: UserRegistrationData): Result<TokenPayload> =
        withContext(Dispatchers.IO) {
            val jsonString = Json.encodeToString(dto.toUserRegistrationRequest())

            val userRequestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
            authorizeService.registration(
                userJson = userRequestBody,
                photo = null,
            )
        }
}