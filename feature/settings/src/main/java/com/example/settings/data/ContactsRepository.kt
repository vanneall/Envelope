package com.example.settings.data

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactsService: ContactsService,
    @ApplicationContext private val context: Context,
) {

    suspend fun fetchUserData() = withContext(Dispatchers.IO) {
        contactsService.fetchUserData()
    }

    suspend fun saveUserData(data: UserProfileUpdateRequest, photoUri: Uri? = null) = withContext(Dispatchers.IO) {
        val photoMultipartBody = photoUri?.let { uriToMultipart(uri = it, context = context) }
        contactsService.putUserData(data, photoMultipartBody)
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