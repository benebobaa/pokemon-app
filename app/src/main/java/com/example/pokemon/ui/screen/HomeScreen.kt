package com.example.pokemon.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pokemon.ui.screen.tab.MyPokemonTab
import com.example.pokemon.ui.screen.tab.PokemonTab
import com.example.pokemon.ui.theme.Purple40
import com.example.pokemon.ui.theme.Purple80
import com.example.pokemon.ui.theme.PurpleGrey80
import com.example.pokemon.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navHostController: NavHostController) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                Purple40
            ),title = {
                Text(text = "Pokemon", style = TextStyle(color = Color.White, fontSize = 30.sp))
            })
        }
    ) {
        TabScreen(modifier = Modifier.padding(it), navHostController, viewModel)
    }
}


@Composable
fun TabScreen(modifier: Modifier, navHostController: NavHostController, viewModel: HomeViewModel) {
    val tabIndex= viewModel.tabIndex

    val tabs = listOf("Pokemon", "My Pokemon")

    Column(modifier = modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = PurpleGrey80,
            contentColor = Purple40,
            indicator = { tabPositions ->
                Box(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                    content = {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Purple40)
                                .height(5.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }
                )
            }) {
            tabs.forEachIndexed { index, title ->
                Tab(selectedContentColor = Color.White,
                    text = {
                        Text(
                            title,
                            style = TextStyle(fontSize = 20.sp)
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { viewModel.tabIndex(index) }
                )
            }
        }
        when (tabIndex) {
            0 -> PokemonTab(navHostController, viewModel)
            1 -> MyPokemonTab(navHostController,viewModel)
        }
    }
}