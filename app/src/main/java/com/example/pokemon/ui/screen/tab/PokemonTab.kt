package com.example.pokemon.ui.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pokemon.ui.screen.component.PokemonCard
import com.example.pokemon.ui.viewmodel.HomeViewModel


@Composable
fun PokemonTab(navHostController: NavHostController, viewModel: HomeViewModel){
    val listPokemon = viewModel.pokemonList.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .background(Color.DarkGray)
            .fillMaxSize()
    ) {
        items(count = listPokemon.value.size) {
            PokemonCard(listPokemon.value[it], navHostController, viewModel)
        }
    }
}

