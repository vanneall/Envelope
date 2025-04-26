package ru.point.core.services.auth.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import ru.point.core.services.auth.models.SigninData
import ru.point.core.services.auth.models.SignupData
import ru.point.core.services.auth.requests.toRequest
import ru.point.core.services.auth.services.AuthorizeService
import java.io.File

internal class AuthorizationRepositoryImpl(
    @ApplicationContext
    private val context: Context,
    private val authorizeService: AuthorizeService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : AuthorizationRepository {

    override suspend fun signIn(signinData: SigninData) = withContext(dispatcher) {
        authorizeService.authorize(signInRequest = signinData.toRequest()).map { response -> response.token }
    }

    override suspend fun singUp(signupData: SignupData) = withContext(dispatcher) {
        val jsonString = Json.encodeToString(signupData.toRequest())
        val photo = signupData.uri?.let { uriToMultipart(it, context) }

        val userRequestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())
        authorizeService.registration(
            userJson = userRequestBody,
            photo = photo,
        ).map { response -> response.token }
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