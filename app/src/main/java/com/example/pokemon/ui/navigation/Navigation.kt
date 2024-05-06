package com.example.pokemon.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemon.ui.screen.HomeScreen
import com.example.pokemon.ui.screen.detail.DetailPokemonScreen
import com.example.pokemon.ui.viewmodel.HomeViewModel
import com.example.pokemon.utils.Constants

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    val viewModel: HomeViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Constants.HOME_SCREEN
    ) {

        composable(Constants.HOME_SCREEN) {
            HomeScreen(viewModel = viewModel, navHostController = navController)
        }

        composable(Constants.DETAIL_POKEMON) {
             DetailPokemonScreen(viewModel= viewModel, navHostController = navController,context)
        }

    }
}