package com.example.pokemon.data.response

import com.google.gson.annotations.SerializedName

data class PokemonFavorite(

	@field:SerializedName("pokemon")
	val pokemon: Pokemon? = null,

	@field:SerializedName("count_update")
	val countUpdate: Int? = null,

	@field:SerializedName("nick_name")
	val nickName: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
