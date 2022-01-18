package com.example.my_application.feature_movielist.data.dto

import com.google.gson.annotations.SerializedName

data class AssetTypeCounts(
    val clip: Int,
    val program: Int,
    val trailer: Int,
    @SerializedName("virtual-channel")
    val virtualChannel: Int
)