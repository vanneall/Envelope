package com.point.auth.authorization.data

import android.util.Log
import com.point.auth.authorization.token.Token
import com.point.auth.registration.presenter.mvi.UserRegistrationData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.LocalDate

class AuthorizationRepositoryImpl(private val authorizeService: AuthorizeService) :
    AuthorizationRepository {

    override suspend fun tryToAuthorize(authRequest: AuthRequest): Result<Token> =
        authorizeService.authorize(authRequest = authRequest)

    override suspend fun registration(dto: UserRegistrationData): Result<Token> {
        val user = UserRegistrationRequest(
            username = dto.login,
            password = dto.password,
            name = dto.name,
            birthDate = LocalDate.now().toString(),
            status = dto.status,
            aboutUser = dto.about,
            isDeveloper = true,
        )

        val jsonString = Json.encodeToString(user)
        val userRequestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

        // TODO
//        val photoPart = photoFile?.let { file ->
//            val requestFile = file
//                .toRequestBody("image/*".toMediaTypeOrNull())
//
//            MultipartBody.Part.createFormData(
//                name = "photo",
//                filename = file.name,
//                body = requestFile
//            )
//        }

        Log.d("mong", jsonString)
        val response = authorizeService.registration(
            userJson = userRequestBody,
            photo = null
        )

        println("Token from server = ${response.getOrNull()}")
        return response
    }
}