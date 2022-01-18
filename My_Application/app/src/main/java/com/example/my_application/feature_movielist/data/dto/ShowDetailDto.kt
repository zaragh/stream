package com.example.my_application.feature_movielist.data.dto

import com.example.my_application.feature_movielist.domain.model.Show

data class ShowDetailDto(
    val description: String?,
    val id: String?,
    val image: String? ,
    val title: String?,
    val type: String?
)

fun ShowDetailDto.toShow(): Show {
    return Show(description=description,
        id = id,
        image = image,
        title = title,
        type = type)
}