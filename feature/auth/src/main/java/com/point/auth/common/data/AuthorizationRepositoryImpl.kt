package com.point.auth.common.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.point.auth.registration.presenter.host.UserRegistrationData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class AuthorizationRepositoryImpl(
    @ApplicationContext
    private val context: Context,
    private val authorizeService: AuthorizeService
) : AuthorizationRepository {

    override suspend fun tryToAuthorize(authRequest: AuthRequest): Result<TokenPayload> =
        withContext(Dispatchers.IO) {
            authorizeService.authorize(authRequest = authRequest)
        }

    override suspend fun registration(dto: UserRegistrationData): Result<TokenPayload> =
        withContext(Dispatchers.IO) {
            val jsonString = Json.encodeToString(dto.toUserRegistrationRequest())
            val photo = dto.uri?.let { uriToMultipart(it, context) }

            val userRequestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
            authorizeService.registration(
                userJson = userRequestBody,
                photo = photo,
            )
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