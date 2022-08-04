package com.example.amanda.data

import com.example.amanda.data.model.Greenhouse
import com.example.amanda.data.model.LoggedInUser
import com.example.amanda.data.model.Sensor
import com.example.amanda.restapi.RestApiManager
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class GreenhouseDataSource {
    suspend fun loadGreenhouse(): Result<List<Greenhouse>>{
        return suspendCoroutine { cont ->
            val restApiManager = RestApiManager()

//            restApiManager.greenhouses(){
//                if (it != null) {
//                    cont.resume(Result.Success(it))
//                }
//                else{
//                    cont.resume(Result.Error(Exception("Error logging in")))
//                }
//            }
        }
    //        val sensors = listOf<Sensor>(
//            Sensor("Electrical", "EC", "kw/h", "1824"),
//            Sensor("Dosing", "DO", "", "7.8"),
//            Sensor("Temperature", "Temp", "Celsius", "22"),
//            Sensor("Carbon Dioxide", "CO2", "m/s", "956"),
//            Sensor("Power of Hydrogen", "pH", "", "6.4")
//        )
//        return listOf<Greenhouse>(
//            Greenhouse("Greenhouse 1", "Balithi, Tomohon", "Chrysanteum", sensors),
//            Greenhouse("Greenhouse 2", "Balithi, Tomohon", "Chrysanteum", sensors),
//            Greenhouse("Greenhouse 3", "Balithi, Tomohon", "Chrysanteum", sensors),
//        )
    }
}