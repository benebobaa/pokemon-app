package com.example.pokemon.data.request

import com.google.gson.annotations.SerializedName

data class CatchPokemon(
    @field:SerializedName("pokemon_id")
    val pokemonId: Int,

    @field:SerializedName("nick_name")
    val nickName: String
)