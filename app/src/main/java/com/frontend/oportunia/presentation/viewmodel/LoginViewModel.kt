package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.frontend.oportunia.domain.model.Student
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

//@HiltViewModel
//class LoginViewModel @Inject constructor(
//    private val repository: StudentRepository
//) : ViewModel() {
//
//    private val _username = MutableStateFlow("")
//    val username: StateFlow<String> get() = _username
//
//    private val _password = MutableStateFlow("")
//    val password: StateFlow<String> get() = _password
//
//    private val _isLoggingIn = MutableStateFlow(false)
//    val isLoggingIn: StateFlow<Boolean> get() = _isLoggingIn
//
//    private val _loginError = MutableStateFlow<String?>(null)
//    val loginError: StateFlow<String?> get() = _loginError
//
//    private val _loginSuccess = MutableStateFlow(false)
//    val loginSuccess: StateFlow<Boolean> get() = _loginSuccess
//
//    private val _loggedStudent = MutableStateFlow<Student?>(null)
//    val loggedStudent: StateFlow<Student?> get() = _loggedStudent
//
//    private val _currentStudent = MutableStateFlow<Student?>(null)
//    val currentStudent: StateFlow<Student?> get() = _currentStudent
//
//
//
//    fun onUsernameChange(value: String) {
//        _username.value = value
//    }
//
//    fun onPasswordChange(value: String) {
//        _password.value = value
//    }
//
//    fun login() {
//        _isLoggingIn.value = true
//        _loginError.value = null
//
//        val user = _username.value.trim()
//        val pass = _password.value.trim()
//
//        viewModelScope.launch {
//            val result = repository.findAllStudents()
//            if (result.isSuccess) {
//                println("Estudiantes obtenidos correctamente")
//
//                val student = result.getOrNull()?.find {
//                    it.user.email == user && it.user.password == pass
//                }
//
//                if (student != null) {
//                    _loginSuccess.value = true
//                    _loggedStudent.value = student // ✅ Guardar en sesión global
//                    _loginError.value = null
//                    //loadStudentDetails(student.user.id)
//                    println("Login exitoso: ${student.user.firstName} ${student.user.lastName}")
//                } else {
//                    _loginError.value = "invalid_credentials"
//                    println("Credenciales incorrectas")
//                }
//            } else {
//                _loginError.value = "network_error"
//                println("Error al obtener estudiantes")
//            }
//
//            _isLoggingIn.value = false
//        }
//    }
//
////    fun loadStudentDetails(studentId: Long) {
////        viewModelScope.launch {
////            _abilities.value = repository.getAbilitiesForStudent(studentId)
////            _experiences.value = repository.getExperiencesForStudent(studentId)
////        }
////    }
//
//    fun setLoggedStudent(student: Student) {
//        _loggedStudent.value = student
//    }
//
//}

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
                    Log.d("LoginViewModel", "Login successful for user: $username")
                }
                .onFailure { exception ->
                    _loginState.value =
                        LoginState.Error(exception.message ?: "Authentication failed")
                    Log.e(
                        "LoginViewModel",
                        "Login failed for user: $username, error: ${exception.message}"
                    )
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
}
