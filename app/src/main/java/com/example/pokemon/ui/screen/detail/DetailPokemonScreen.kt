package com.example.pokemon.ui.screen.detail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.pokemon.data.request.CatchPokemon
import com.example.pokemon.data.request.UpdatePokemon
import com.example.pokemon.data.response.Pokemon
import com.example.pokemon.ui.viewmodel.HomeViewModel
import com.example.pokemon.ui.viewmodel.ReleasePokemonListener

@Composable
fun DetailPokemonScreen(
    viewModel: HomeViewModel,
    navHostController: NavHostController,
    context: Context,
) {
    val fromFavorite = viewModel.fromFavoriteTab
    val pokemon = viewModel.selectedPokemon
    val favPokemon = viewModel.selectedFavPokemon

    val showDialog = remember { mutableStateOf(false) }

    val releasePokemonListener = remember {
        object : ReleasePokemonListener {
            override fun onReleaseSuccess() {
                navHostController.popBackStack()
            }
        }
    }
    viewModel.releasePokemonListener = releasePokemonListener

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Text(
                text = pokemon?.name.toString().replaceFirstChar { it.uppercase() },
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            if (fromFavorite){
                Text(
                    text = "Nickname: ${favPokemon?.nickName}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }else {
                Text(
                    text = "Type: ${pokemon?.type}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
            Text(
                text = "${pokemon?.move}",
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(pokemon?.imageUrl),
                    contentDescription = "Pokemon Image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))

            if (fromFavorite) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)

                ) {
                    Button(
                        onClick = {
                            showDialog.value = true
                        },
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .weight(1f) // Equal weight for both buttons
                            .fillMaxWidth()
                    ) {
                        Text(text = "Update")
                    }
                    Spacer(modifier = Modifier.width(24.dp))
                    Button(
                        onClick = {
                           viewModel.releasePokemon(favPokemon?.id!!, context)
                        },
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .weight(1f) // Equal weight for both buttons
                            .fillMaxWidth()
                    ) {
                        Text(text = "Release")
                    }
                }

            } else {
                Button(
                    onClick = {
                        showDialog.value = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    if (viewModel.loading) {
                        CircularProgressIndicator()
                    } else {
                        Text(text = "Catch")
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.5f))
        }
    }

    if (showDialog.value) {
        AlertDialog(
            showDialog = showDialog,
            viewModel = viewModel,
            pokemon = pokemon,
            context = context,
            favPokemonId = favPokemon?.id,
            fromFavorite = fromFavorite
        )
    }
}

@Composable
fun AlertDialog(
    showDialog: MutableState<Boolean>,
    viewModel: HomeViewModel,
    pokemon: Pokemon?, // Assuming Pokemon is your data class representing a Pokemon
    context: Context,
    favPokemonId: Int?,
    fromFavorite: Boolean = false
) {

    val nickname = remember { mutableStateOf(TextFieldValue()) }

    MaterialTheme {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when requested
                showDialog.value = false
            },
            title = {
                if (fromFavorite){
                    Text("Update Nickname")
                } else {
                    Text("Catch Pokemon")
                }
            },
            text = {
                Column {
                    if (fromFavorite){
                        Text("Update a new nickname for ${pokemon?.name}:")
                    }else {
                        Text("Enter a nickname for ${pokemon?.name}:")
                    }
                    // TextField to input the nickname
                    TextField(
                        value = nickname.value,
                        onValueChange = { nickname.value = it },
                        label = { Text("Nickname") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if(fromFavorite){
                            viewModel.updatePokemon(
                                favPokemonId!!,
                                request = UpdatePokemon(
                                    nickName = nickname.value.text
                                ),
                                context
                            )
                        } else {
                            viewModel.catchPokemon(
                                request = CatchPokemon(
                                    pokemonId = pokemon?.id!!,
                                    nickName = nickname.value.text // Use the entered nickname
                                ),
                                context
                            )
                        }
                        showDialog.value = false
                    }
                ) {
                    if (fromFavorite){
                        Text("Update")
                    } else {
                        Text("Catch")
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        // Dismiss the dialog when "Cancel" is clicked
                        showDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}