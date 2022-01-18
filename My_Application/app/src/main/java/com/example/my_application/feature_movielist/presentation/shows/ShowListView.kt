package com.example.my_application.feature_movielist.presentation.shows

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.my_application.feature_movielist.presentation.shows.component.ShowListItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@DelicateCoroutinesApi
@Composable
fun ShowListView(viewModel: ShowsViewModel = hiltViewModel()) {

    val state = viewModel.status.value
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    val expandedCardIds = viewModel.expandedCardIdsList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            content = {
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.getShows()
                        isRefreshing = true
                    },
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.shows) { show ->
                            ShowListItem(
                                show = show,
                                onCardArrowClick = { viewModel.onCardArrowClicked(show.id) },
                                expanded = expandedCardIds.value.contains(show.id),
                            )
                        }
                    }
                }

                LaunchedEffect(isRefreshing) {
                    if (isRefreshing) {
                        delay(1000L)
                        isRefreshing = false
                    }
                }

                if (state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .align(Alignment.Center)
                    )
                }
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            },
        )
    }
}
