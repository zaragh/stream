package com.example.my_application.feature_movielist.domain.repository

import com.example.my_application.feature_movielist.data.dto.ShowDetailDto

interface ShowRepository {

    suspend fun getShows(): List<ShowDetailDto>
}