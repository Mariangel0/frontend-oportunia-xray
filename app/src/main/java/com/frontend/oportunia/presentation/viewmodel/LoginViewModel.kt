package com.frontend.oportunia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: StudentRepository) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _isLoggingIn = MutableStateFlow(false)
    val isLoggingIn: StateFlow<Boolean> get() = _isLoggingIn

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> get() = _loginError

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> get() = _loginSuccess

    private val _currentStudent = MutableStateFlow<Student?>(null)
    val currentStudent: StateFlow<Student?> get() = _currentStudent

    private val _abilities = MutableStateFlow<List<Ability>>(emptyList())
    val abilities: StateFlow<List<Ability>> get() = _abilities

    private val _experiences = MutableStateFlow<List<Experience>>(emptyList())
    val experiences: StateFlow<List<Experience>> get() = _experiences

    fun onUsernameChange(value: String) {
        _username.value = value
    }

    fun onPasswordChange(value: String) {
        _password.value = value
    }

    fun login() {
        _isLoggingIn.value = true
        _loginError.value = null

        val user = _username.value.trim()
        val pass = _password.value

        viewModelScope.launch {
            val result = repository.findAllStudents()

            val student = result.getOrNull()?.find {
                it.user.email == user && it.user.password == pass
            }

            if (student != null) {
                _currentStudent.value = student
                _loginSuccess.value = true
                _loginError.value = null

                // Cargar habilidades y experiencias del estudiante
                loadStudentDetails(student.user.id)

                println("Login exitoso: ${student.user.firstName} ${student.user.lastName}")
            } else {
                _loginError.value = "invalid_credentials"
            }

            _isLoggingIn.value = false
        }
    }

    fun loadStudentDetails(studentId: Long) {
        viewModelScope.launch {
            _abilities.value = repository.getAbilitiesForStudent(studentId)
            _experiences.value = repository.getExperiencesForStudent(studentId)
        }
    }
}
