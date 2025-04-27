package com.point.services.media.repository

import android.net.Uri

interface MediaRepository {

    suspend fun uploadPhoto(uri: Uri): Result<Long>
}