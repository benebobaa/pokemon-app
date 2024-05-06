package com.example.pokemon.ui.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokemon.ui.screen.component.MyPokemonCard
import com.example.pokemon.ui.viewmodel.HomeViewModel


@Composable
fun MyPokemonTab(navHostController: NavHostController, viewModel: HomeViewModel) {
    val listPokemon = viewModel.pokemonFavList.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray)
    ) {
        if (listPokemon.value.isEmpty()) {
            Text(
                text = "No favorite Pokémon found",
                style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxSize()
            ) {
                items(count = listPokemon.value.size) {
                    MyPokemonCard(listPokemon.value[it], navHostController, viewModel)
                }
            }
        }
    }
}