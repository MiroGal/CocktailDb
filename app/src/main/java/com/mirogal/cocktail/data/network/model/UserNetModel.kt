package com.mirogal.cocktail.data.network.model

import com.google.gson.annotations.SerializedName

class UserNetModel(
        @SerializedName("id")
        val id: Long = 1L,

        @SerializedName("name")
        val name: String = "",

        @SerializedName("lastName")
        val lastName: String = "",

        @SerializedName("avatar")
        val avatar: String?
)