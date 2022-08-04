package com.example.amanda.data.model

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("label") val label: List<String> = listOf(),
    @SerializedName("data") val data: List<Float> = listOf(),
    @SerializedName("unit") val unit: String = ""
)
{}