package com.example.amanda.restapi

import android.util.Log
import com.example.amanda.data.model.Data
import com.example.amanda.data.model.Greenhouse
import com.example.amanda.data.model.LoggedInUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiManager {
    fun login(userData: LoggedInUser, onResult: (LoggedInUser?) -> Unit) {
        val retrofit = ServiceBuilder.buildService(Login::class.java)
        retrofit.login(userData).enqueue(
            object : Callback<LoggedInUser> {
                override fun onFailure(call: Call<LoggedInUser>, t: Throwable) {
                    Log.e("RESTF", t.stackTraceToString())
                    onResult(null)
                }
                override fun onResponse( call: Call<LoggedInUser>, response: Response<LoggedInUser>) {
                    val user = response.body()
                    Log.d("RESTR", user.toString())
                    onResult(user)
                }
            }
        )
    }

    fun getGreenhouses(token: String?, onResult: (List<Greenhouse>?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Greenhouses::class.java)
        retrofit.getLists(token).enqueue(
            object : Callback<List<Greenhouse>> {
                override fun onFailure(call: Call<List<Greenhouse>>, t: Throwable) {
                    Log.e("RESTF", t.stackTraceToString())
                    onResult(null)
                }
                override fun onResponse( call: Call<List<Greenhouse>>, response: Response<List<Greenhouse>>) {
                    val test = response.body()
                    Log.d("CALL", call.request().toString())
                    Log.d("RESTR", response.toString())
                    onResult(test)
                }
            }
        )
    }

    fun getData(token: String?,
                greenhouse_id: String?,
                sensor: String?,
                date: String?,
                onResult: (Data?) -> Unit){
        val retrofit = ServiceBuilder.buildService(Sensor::class.java)
        retrofit.getData(token, greenhouse_id, sensor, date).enqueue(
            object : Callback<Data> {
                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("RESTF", t.stackTraceToString())
                    onResult(null)
                }
                override fun onResponse( call: Call<Data>, response: Response<Data>) {
                    Log.d("CALL", call.request().toString())
                    val test = response.body()
                    onResult(test)
                }
            }
        )
    }
}