package com.example.pokemon.data.response

import com.google.gson.annotations.SerializedName

data class BaseResponseList<T>(

    @field:SerializedName("data")
    val data: List<T>? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class BaseResponse<T>(

    @field:SerializedName("data")
    val data: T? = null,

    @field:SerializedName("message")
    val message: String? = null
)
