package com.example.my_application.feature_movielist.data

import com.example.my_application.feature_movielist.data.dto.ShowDto
import retrofit2.http.GET

interface ShowsApi {

    @GET("/assets?client=android-code-test")
    suspend fun getShows(): ShowDto
}