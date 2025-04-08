package com.point.chats.dialog.data

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

class MediaContentRepository @Inject constructor(
    @ApplicationContext
    private val applicationContext: Context,
    private val mediaContentService: MediaContentService,
) {

    suspend fun uploadPhoto(uri: Uri) = withContext(Dispatchers.IO) {
        mediaContentService.uploadPhoto(uriToMultipart(context = applicationContext, uri = uri))
    }

    private fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)!!
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