package com.example.pokemon.data.response

import com.google.gson.annotations.SerializedName


data class Pokemon(

	@field:SerializedName("move")
	val move: String? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("weight")
	val weight: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("height")
	val height: Int? = null
)
