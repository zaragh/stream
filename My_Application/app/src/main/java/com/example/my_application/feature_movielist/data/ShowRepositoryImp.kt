package com.example.my_application.feature_movielist.data

import com.example.my_application.feature_movielist.data.dto.ShowDetailDto
import com.example.my_application.feature_movielist.domain.repository.ShowRepository

class ShowRepositoryImp(private val showsApi: ShowsApi) : ShowRepository {
    override suspend fun getShows(): List<ShowDetailDto> {
        return showsApi.getShows().data
    }

}