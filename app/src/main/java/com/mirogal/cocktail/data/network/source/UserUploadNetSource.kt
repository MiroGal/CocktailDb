package com.mirogal.cocktail.data.network.source

import com.mirogal.cocktail.data.network.source.base.BaseNetSource
import java.io.File

interface UserUploadNetSource : BaseNetSource {

    suspend fun updateUserAvatar(avatar: File, onProgressChanged: (
            fraction: Float, processedLength: Long, totalLength: Long
    ) -> Unit = { _, _, _ ->}): String

}