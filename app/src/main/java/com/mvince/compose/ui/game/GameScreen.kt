package com.mvince.compose.ui.game

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mvince.compose.ui.Route
import com.mvince.compose.ui.theme.*


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameScreen(navHostController: NavHostController) {
    val viewModel = hiltViewModel<GameViewModel>()
    val currentQuestion = viewModel.currentQuestion.collectAsState().value
    val gameScore = viewModel.gameScore.collectAsState().value
    val gameState = viewModel.gameState.collectAsState().value
    val numQuestion = viewModel.numQuestion.collectAsState().value +1

    if(gameState == true){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "TrivialPoursuit",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                )
            },
            content = {
                Column(
                    modifier = Modifier.padding(top = 65.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (currentQuestion != null) {
                        Box(
                            modifier = if (currentQuestion.category.contains("Geography")) {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Blue)
                            } else if (currentQuestion.category.contains("Entertainment")) {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Pink)
                            } else if (currentQuestion.category.contains("History")) {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Yellow)
                            } else if (currentQuestion.category.contains("Art") || currentQuestion.category.contains(
                                    "Mythology"
                                )
                            ) {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Purple)
                            } else if (currentQuestion.category.contains("Science") || currentQuestion.category.contains(
                                    "Animals"
                                )
                            ) {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Green)
                            } else if (currentQuestion.category.contains("Sports") || currentQuestion.category.contains(
                                    "Celebrities"
                                )
                            ) {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Orange)
                            } else {
                                Modifier
                                    .padding(bottom = 16.dp)
                                    .background(Color.LightGray)
                            }
                        ) {
                            Text(
                                text = Html.fromHtml(currentQuestion.category).toString(),
                                Modifier
                                    .padding(12.dp),
                                color = if (currentQuestion.category.contains("Geography") || currentQuestion.category.contains(
                                        "Entertainment"
                                    ) || currentQuestion.category.contains("Art") || currentQuestion.category.contains(
                                        "Mythology"
                                    ) || currentQuestion.category.contains("Science") || currentQuestion.category.contains(
                                        "Animals"
                                    ) || currentQuestion.category.contains("Sports") || currentQuestion.category.contains(
                                        "Celebrities"
                                    )
                                ) {
                                    Color.White
                                } else {
                                    Color.Black
                                }
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "Question : $numQuestion/10")

                            Row {
                                if (currentQuestion.difficulty == "easy") {
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = Green
                                    )
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_circle),
                                        contentDescription = null,
                                        tint = Green,
                                        modifier = Modifier
                                            .padding(top = 8.dp)
                                            .size(10.dp)
                                    )
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_circle),
                                        contentDescription = null,
                                        tint = Green,
                                        modifier = Modifier
                                            .padding(top = 8.dp, start = 3.dp)
                                            .size(10.dp)
                                    )
                                } else if (currentQuestion.difficulty == "medium") {
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = Orange
                                    )
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = Orange
                                    )
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_circle),
                                        contentDescription = null,
                                        tint = Orange,
                                        modifier = Modifier
                                            .padding(top = 8.dp, start = 3.dp)
                                            .size(10.dp)
                                    )
                                } else {
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                    Icon(
                                        painter = painterResource(id = com.mvince.compose.R.drawable.ic_star),
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                }
                            }
                        }

                        Text(
                            text = Html.fromHtml(currentQuestion.question).toString(),
                            modifier = Modifier.padding(16.dp)
                        )
                        viewModel.response.collectAsState().value?.forEach {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp, 8.dp),
                                onClick = { viewModel.validateAnswer(it) }
                            ) {
                                Text(text = Html.fromHtml(it.reponse).toString())
                            }
                        }
                        Text(text = "Score : $gameScore")
                    }
                }
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "BRAVO! Vous avez fini la partie d'aujourd'hui avec le score de : $gameScore",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 20.dp).padding(bottom = 15.dp)
            )
            Button(
                onClick = { navHostController.navigate(Route.END_GAME) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Finir la partie")
            }
        }
    }
}