package com.example.pokemon.network

import com.example.pokemon.data.request.CatchPokemon
import com.example.pokemon.data.request.UpdatePokemon
import com.example.pokemon.data.response.BaseResponse
import com.example.pokemon.data.response.BaseResponseList
import com.example.pokemon.data.response.Pokemon
import com.example.pokemon.data.response.PokemonFavorite
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon")
    fun getAllPokemon(): Call<BaseResponseList<Pokemon>>

    @GET("pokemon/favorite")
    fun getAllFavorite(): Call<BaseResponseList<PokemonFavorite>>

    @POST("pokemon/favorite")
    fun catchPokemon(@Body request: CatchPokemon): Call<BaseResponse<PokemonFavorite>>

    @PUT("pokemon/favorite/{id}")
    fun updatePokemon(@Path("id") id: Int, @Body request: UpdatePokemon): Call<BaseResponse<PokemonFavorite>>

    @DELETE("pokemon/favorite/{id}")
    fun releasePokemon(@Path("id") id: Int): Call<BaseResponse<PokemonFavorite>>
}