package com.example.pokemon.data.request

import com.google.gson.annotations.SerializedName

data class UpdatePokemon(
    @field:SerializedName("nick_name")
    val nickName: String
)