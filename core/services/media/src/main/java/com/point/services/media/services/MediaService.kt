package com.point.services.media.services

import com.point.services.media.responses.PhotoIdResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

internal interface MediaService {

    @Multipart
    @POST("/photos")
    suspend fun uploadPhoto(@Part photo: MultipartBody.Part): Result<PhotoIdResponse>
}