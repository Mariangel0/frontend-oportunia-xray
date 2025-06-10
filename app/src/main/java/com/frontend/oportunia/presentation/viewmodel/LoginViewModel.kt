package com.frontend.oportunia.presentation.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import com.frontend.oportunia.domain.model.Student
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


sealed class LoginState {


    data object Initial : LoginState()
    data object Loading : LoginState()
    data object Success : LoginState()
    data class Error(val message: String) : LoginState()
}


data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val username: String = "",
    val password: String = ""
)




@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {


    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> = _loggedUser


    private val _loginState = MutableStateFlow<LoginState>(LoginState.Initial)
    val loginState = _loginState.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()


    init {
        checkAuthenticationStatus()
    }


    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            authRepository.isAuthenticated()
                .onSuccess { authenticated ->
                    _isLoggedIn.value = authenticated
                    Log.d("LoginViewModel", "Authentication status checked: $authenticated")
                }
                .onFailure {
                    Log.e("LoginViewModel", "Error checking authentication status: ${it.message}")
                }
        }
    }


    fun login(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _loginState.value = LoginState.Loading


            // Validate inputs before attempting network call
            if (!isValidEmail(username)) {
                _loginState.value = LoginState.Error("Invalid email format")
                _isLoading.value = false
                return@launch
            }


            if (password.length < 5) {
                _loginState.value = LoginState.Error("Password must be at least 5 characters")
                _isLoading.value = false
                return@launch
            }


            authRepository.login(username, password)
                .onSuccess {
                    _loginState.value = LoginState.Success
                    _isLoggedIn.value = true
                    authRepository.getCurrentUser()
                        .onSuccess { user ->
                            _loggedUser.value = user
                            Log.d("LoginViewModel", "User loaded: ${user?.firstName}")
                        }
                        .onFailure {
                            Log.e("LoginViewModel", "Failed to load current user after login: ${it.message}")
                        }

                    Log.d("LoginViewModel", "Login successful for user: $username")
                }



            _isLoading.value = false
        }
    }


    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }


    fun logout() {
        viewModelScope.launch {
            _isLoading.value = true


            authRepository.logout()
                .onSuccess {
                    _isLoggedIn.value = false
                    _loginState.value = LoginState.Initial
                    Log.d("LoginViewModel", "User logged out successfully")
                }
                .onFailure { exception ->
                    Log.e("LoginViewModel", "Logout failed: ${exception.message}")
                    _isLoggedIn.value = false
                    _loginState.value = LoginState.Initial
                }


            _isLoading.value = false
        }
    }


    fun resetLoginState() {
        _loginState.value = LoginState.Initial
    }



    fun getUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
                .onSuccess { user ->
                    _loggedUser.value = user
                    Log.d("LoginViewModel", "User recargado: ${user?.firstName}")
                }
                .onFailure {
                    Log.e("LoginViewModel", "Error al recargar el usuario: ${it.message}")
                }
        }
    }

}
