package com.point.chats.dialog.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MediaContentService {

    @Multipart
    @POST("/photos")
    suspend fun uploadPhoto(@Part photo: MultipartBody.Part): Result<PhotoIdResponse>

}

@Serializable
data class PhotoIdResponse(@SerialName("id") val id: Long)