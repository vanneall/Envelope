package com.point.services.media.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.point.services.media.services.MediaService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

internal class MediaRepositoryImpl(
    private val context: Context,
    private val mediaService: MediaService
) : MediaRepository {

    override suspend fun uploadPhoto(uri: Uri) = withContext(Dispatchers.IO) {
        mediaService.uploadPhoto(requireNotNull(uriToMultipart(context = context, uri = uri)))
            .map { response -> response.id }
    }

    private fun uriToMultipart(uri: Uri, context: Context): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val fileName = getFileName(uri, contentResolver) ?: "image.jpg"
        val tempFile = File.createTempFile("upload_", fileName, context.cacheDir).apply {
            outputStream().use { fileOut -> inputStream.copyTo(fileOut) }
        }

        val requestBody = tempFile.asRequestBody(contentResolver.getType(uri)!!.toMediaTypeOrNull())

        return MultipartBody.Part.createFormData("file", fileName, requestBody)
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