package com.frontend.oportunia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class LoginViewModel () : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> get() = _isLoggingIn

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> get() = _loginError

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun login() { // Prueba de login
        _isLoggingIn.value = true
        _loginError.value = null

        val user = _username.value.trim()
        val pass = _password.value

        _loginError.value = when {
            user.isEmpty() || pass.isEmpty() -> "empty_fields"
            user == "estudiante" && pass == "1234" -> {
                println("Login exitoso")
                null
            }
            else -> "invalid_credentials"
        }

        _isLoggingIn.value = false
    }

}
