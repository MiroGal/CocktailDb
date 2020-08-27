package com.mirogal.cocktail.data.network.impl.service

import com.google.gson.JsonObject
import com.mirogal.cocktail.data.network.Constant.Header.TOKEN_HEADER
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

@JvmSuppressWildcards
interface UserUploadService {

    @Multipart
    @POST("users/avatar")
    @Headers(TOKEN_HEADER)
    suspend fun uploadUserAvatar(@Part avatar: MultipartBody.Part): JsonObject

}