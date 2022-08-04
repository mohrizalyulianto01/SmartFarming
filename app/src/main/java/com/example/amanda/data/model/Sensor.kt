package com.example.amanda.data.model

import com.google.gson.annotations.SerializedName

data class Sensor(
    @SerializedName("name") val name: String = "",
    @SerializedName("variable_name") val variable_name: String = "",
    @SerializedName("last_value") val last_value: String = "",
    @SerializedName("unit") val unit: String = ""
)
//data class Sensor(val sensorName: String,
//                  val sensorShortName: String,
//                  val sensorUnit: String,
//                  val sensorLastValue: String)
{}