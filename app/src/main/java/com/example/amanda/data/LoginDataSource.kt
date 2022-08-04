package com.example.amanda.data

import android.util.Log
import com.example.amanda.R
import com.example.amanda.data.model.LoggedInUser
import com.example.amanda.restapi.RestApiManager
import com.example.amanda.ui.login.LoggedInUserView
import com.example.amanda.ui.login.LoginResult
import okhttp3.OkHttpClient
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return suspendCoroutine { cont ->
            val restApiManager = RestApiManager()
            val user = LoggedInUser(
                email = username,
                password = password,
                deviceName = "Testing demo device"
            )

            restApiManager.login(user){
                if (it != null) {
                    if(it?.id.isNullOrEmpty()){
                        cont.resume(Result.Error(Exception("Error logging in")))
                    }
                    else{
                        cont.resume(Result.Success(it))
                    }
                }
                else{
                    cont.resume(Result.Error(Exception("Error logging in")))
                }
            }
        }
    //        try {
//            // TODO: handle loggedInUser authentication
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            return Result.Success(fakeUser)
//        } catch (e: Throwable) {
//            return Result.Error(IOException("Error logging in", e))
//        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}