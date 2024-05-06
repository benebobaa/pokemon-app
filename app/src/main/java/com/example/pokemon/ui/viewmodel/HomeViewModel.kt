package com.example.pokemon.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.request.CatchPokemon
import com.example.pokemon.data.request.UpdatePokemon
import com.example.pokemon.data.response.BaseResponse
import com.example.pokemon.data.response.BaseResponseList
import com.example.pokemon.data.response.Pokemon
import com.example.pokemon.data.response.PokemonFavorite
import com.example.pokemon.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel: ViewModel() {

    private val _pokemonList: MutableStateFlow<List<Pokemon>> = MutableStateFlow(listOf())
    private val _favPokemonList: MutableStateFlow<List<PokemonFavorite>> = MutableStateFlow(listOf())

    var selectedPokemon by mutableStateOf<Pokemon?>(null)
        private set

    var selectedFavPokemon by mutableStateOf<PokemonFavorite?>(null)
        private set

    var fromFavoriteTab by mutableStateOf(false)
        private set

    val pokemonList: StateFlow<List<Pokemon>>  = _pokemonList
    val pokemonFavList: StateFlow<List<PokemonFavorite>>  = _favPokemonList

    var errorString: String = " "
    var loading: Boolean by mutableStateOf(false)

    var tabIndex by mutableIntStateOf(0)
        private set

    var releasePokemonListener: ReleasePokemonListener? = null

    init {
        retrievePokemonList()
        retrieveMyPokemon()
    }

    fun tabIndex(index: Int){
        viewModelScope.launch {
            tabIndex = index
        }
    }

    fun selectedPokemon(pokemon: Pokemon){
        viewModelScope.launch {
            selectedPokemon = pokemon
        }
    }

    fun selectedFavoritePokemon(favPokemon: PokemonFavorite){
        viewModelScope.launch {
            selectedFavPokemon = favPokemon
        }
    }

    fun fromFavoriteTab(favTab: Boolean){
        viewModelScope.launch {
            fromFavoriteTab = favTab
        }
    }

    private fun retrievePokemonList() {
        viewModelScope.launch {
            loading = true
            val call: Call<BaseResponseList<Pokemon>> = RetrofitClient.apiService.getAllPokemon()

            call.enqueue(object: Callback<BaseResponseList<Pokemon>> {
                override fun onResponse(
                    call: Call<BaseResponseList<Pokemon>>,
                    response: Response<BaseResponseList<Pokemon>>
                ) {
                    if (response.isSuccessful) {
                        val responseData: List<Pokemon>? = response.body()?.data

                        if (responseData != null) {
                            _pokemonList.value = responseData
                        }
                    } else {
                        val message: String? = response.body()?.message
                        if (message != null) {
                            errorString = message
                        }
                        errorString = response.message()
                    }
                    loading = false
                }

                override fun onFailure(call: Call<BaseResponseList<Pokemon>>, t: Throwable) {
                    errorString = t.message.toString()
                    loading = false

                    Log.d("HomeViewModel", "Error: ${t.message}")
                }
            })
        }
    }

    private fun retrieveMyPokemon() {
        viewModelScope.launch {
            loading = true
            val call: Call<BaseResponseList<PokemonFavorite>> = RetrofitClient.apiService.getAllFavorite()

            call.enqueue(object: Callback<BaseResponseList<PokemonFavorite>> {
                override fun onResponse(
                    call: Call<BaseResponseList<PokemonFavorite>>,
                    response: Response<BaseResponseList<PokemonFavorite>>
                ) {
                    if (response.isSuccessful) {
                        val responseData: List<PokemonFavorite>? = response.body()?.data

                        if (responseData != null) {
                            _favPokemonList.value = responseData
                        } else {
                            _favPokemonList.value = listOf()
                        }

                    } else {
                        val message: String? = response.body()?.message
                        if (message != null) {
                            errorString = message
                        }
                        errorString = response.message()
                    }
                    loading = false
                }

                override fun onFailure(call: Call<BaseResponseList<PokemonFavorite>>, t: Throwable) {
                    errorString = t.message.toString()
                    loading = false

                    Log.d("HomeViewModel", "Error: ${t.message}")
                }
            })
        }
    }

    public fun catchPokemon(request: CatchPokemon, context: Context) {
        loading = true
        viewModelScope.launch {
            val call: Call<BaseResponse<PokemonFavorite>> = RetrofitClient.apiService.catchPokemon(request)

            call.enqueue(object : Callback<BaseResponse<PokemonFavorite>> {
                override fun onResponse(
                    call: Call<BaseResponse<PokemonFavorite>>,
                    response: Response<BaseResponse<PokemonFavorite>>
                ) {
                    if (response.isSuccessful) {
                        val message: String? = response.body()?.message
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        retrieveMyPokemon()
                    }
                    loading = false
                }

                override fun onFailure(call: Call<BaseResponse<PokemonFavorite>>, t: Throwable) {
                    errorString = t.message.toString()
                    loading = false
                    Log.d("HomeViewModel", "Error: ${t.message}")
                }
            })
        }
    }


    public fun updatePokemon(id: Int, request: UpdatePokemon, context: Context) {
        viewModelScope.launch {
            loading = true
            val call: Call<BaseResponse<PokemonFavorite>> = RetrofitClient.apiService.updatePokemon(id,request)

            call.enqueue(object : Callback<BaseResponse<PokemonFavorite>> {
                override fun onResponse(
                    call: Call<BaseResponse<PokemonFavorite>>,
                    response: Response<BaseResponse<PokemonFavorite>>
                ) {
                    if (response.isSuccessful) {
                        val message: String? = response.body()?.message
                        response.body()?.data?.let { selectedFavoritePokemon(it) }
                        response.body()?.data?.pokemon?.let { selectedPokemon(it) }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        retrieveMyPokemon()
                    }
                    loading = false
                }

                override fun onFailure(call: Call<BaseResponse<PokemonFavorite>>, t: Throwable) {
                    errorString = t.message.toString()
                    loading = false
                    Log.d("HomeViewModel", "Error: ${t.message}")
                }
            })
        }
    }

    public fun releasePokemon(id: Int, context: Context) {
        viewModelScope.launch {
            loading = true
            val call: Call<BaseResponse<PokemonFavorite>> = RetrofitClient.apiService.releasePokemon(id)

            call.enqueue(object : Callback<BaseResponse<PokemonFavorite>> {
                override fun onResponse(
                    call: Call<BaseResponse<PokemonFavorite>>,
                    response: Response<BaseResponse<PokemonFavorite>>
                ) {
                    if (response.isSuccessful) {
                        val message: String? = response.body()?.message
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                        if (response.code() == 201){
                            retrieveMyPokemon()
                            releasePokemonListener?.onReleaseSuccess()
                        }
                    }
                    loading = false
                }

                override fun onFailure(call: Call<BaseResponse<PokemonFavorite>>, t: Throwable) {
                    errorString = t.message.toString()
                    loading = false
                    Log.d("HomeViewModel", "Error: ${t.message}")
                }
            })
        }

    }
}

interface ReleasePokemonListener {
    fun onReleaseSuccess()
}