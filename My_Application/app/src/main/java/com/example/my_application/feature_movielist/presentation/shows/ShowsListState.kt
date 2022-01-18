package com.example.my_application.feature_movielist.presentation.shows

import com.example.my_application.feature_movielist.domain.model.Show

data class ShowsListState(
    val isLoading: Boolean = false,
    val shows: List<Show> = emptyList(),
    val error: String = ""
)
