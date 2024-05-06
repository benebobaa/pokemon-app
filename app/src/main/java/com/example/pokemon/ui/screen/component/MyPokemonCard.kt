package com.example.pokemon.ui.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokemon.data.response.PokemonFavorite
import com.example.pokemon.ui.viewmodel.HomeViewModel
import com.example.pokemon.utils.Constants

@Composable
fun MyPokemonCard(
    favPokemon: PokemonFavorite, navHostController: NavHostController, viewModel: HomeViewModel
) {
    Box (
        modifier = Modifier
            .height(200.dp)
            .background(Color.Transparent)
            .padding(horizontal = 6.dp)
            .clickable {
                viewModel.fromFavoriteTab(true)
                viewModel.selectedPokemon(favPokemon.pokemon!!)
                viewModel.selectedFavoritePokemon(favPokemon)
                navHostController.navigate(Constants.DETAIL_POKEMON)
            }
    ){
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                // Information about the Pokémon on the left
                Column {
                    Text(
                        text = favPokemon.pokemon?.name.toString().replaceFirstChar { it.uppercase() },
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        text = favPokemon.nickName.toString(),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Type: ${favPokemon.pokemon?.type}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Weight: ${favPokemon.pokemon?.weight} kg",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Height: ${favPokemon.pokemon?.height} m",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(16.dp)) // Add some space between the text and the image

                // Image on the right
                Image(
                    painter = rememberAsyncImagePainter(favPokemon.pokemon?.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(shape = RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}
