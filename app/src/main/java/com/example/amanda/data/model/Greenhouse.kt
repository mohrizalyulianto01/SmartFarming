package com.example.amanda.data.model

import com.google.gson.annotations.SerializedName

data class Greenhouse (@SerializedName("_id") val id: String = "",
                       @SerializedName("name") val name: String = "",
                       @SerializedName("location") val location: String = "",
                       @SerializedName("commodity") val commodity: String = "",
                       @SerializedName("last_update") val last_update: String = "",
                       @SerializedName("sensors") val sensors: List<Sensor> = listOf()
                       )
