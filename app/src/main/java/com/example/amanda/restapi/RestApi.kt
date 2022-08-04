package com.example.amanda.restapi

import com.example.amanda.data.model.Data
import com.example.amanda.data.model.Greenhouse
import com.example.amanda.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.http.*

interface Login {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json")
    @POST("login")
    fun login(@Body userData: LoggedInUser): Call<LoggedInUser>
}

interface Greenhouses{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json")
    @GET("greenhouses")
    fun getLists(@Header("Authorization") token: String?): Call<List<Greenhouse>>
}

interface Sensor{
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json")
    @GET("data/sensor")
    fun getData(@Header("Authorization") token: String?,
                @Query("greenhouse_id") greenhouse_id: String?,
                @Query("sensor") sensor: String?,
                @Query("date") date: String?
//                @Query("average") average: Boolean = true
    ): Call<Data>
}