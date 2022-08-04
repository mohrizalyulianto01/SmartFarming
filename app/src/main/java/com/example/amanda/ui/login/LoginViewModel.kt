package com.example.amanda.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.amanda.R
import com.example.amanda.data.LoginRepository
import com.example.amanda.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        MainScope().launch {
            val result = withContext(Dispatchers.IO) {
                loginRepository.login(username, password)
            }
            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.name,
                        token = result.data.token))
            } else {
                _loginResult.value = LoginResult(error = R.string.login_failed)
            }
        }
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(displayName = result.data.name))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }

//        val restApiManager = RestApiManager()
//        val user = LoggedInUser(
//            email = "administrator@example.com",
//            password = "administrator",
//            deviceName = "Testing device"
//        )
//        restApiManager.login(user){
//            if (it != null) {
//                if(it?.id.isNullOrEmpty()){
//                    _loginResult.value = LoginResult(error = R.string.login_failed)
//                }
//                else{
//                    _loginResult.value = LoginResult(success = LoggedInUserView(displayName = it.name))
//                }
//            }
//            else{
//                _loginResult.value = LoginResult(error = R.string.login_failed)
//            }
//        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
//            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}