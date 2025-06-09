package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.remote.dto.UserDto
import com.frontend.oportunia.domain.model.Role
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val repository: StudentRepository
) : ViewModel() {

    // Datos personales
    val name = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val birthDate = MutableStateFlow<Long?>(null)
    val selectedImageUri = MutableStateFlow<Uri?>(null)
    val education = MutableStateFlow<List<String>>(emptyList())
    val isWorking = MutableStateFlow(false)
    val company = MutableStateFlow("")
    val jobPosition = MutableStateFlow("")

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun addEducation() {
        education.value = education.value + ""
    }

    fun updateEducation(index: Int, value: String) {
        val updated = education.value.toMutableList().apply {
            this[index] = value
        }
        education.value = updated
    }

    fun removeEducation(index: Int) {
        val updated = education.value.toMutableList().apply {
            removeAt(index)
        }
        education.value = updated
    }

    fun onImageSelected(uri: Uri?) {
        selectedImageUri.value = uri
    }

    fun clearError() {
        _error.value = null
    }

    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    fun registerStudent(onSuccess: () -> Unit) {
        if (name.value.isBlank() || lastName.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || birthDate.value == null  ) {
            _error.value = "empty_fields"
            return
        }

        if (password.value != confirmPassword.value) {
            _error.value = "password_mismatch"
            return
        }

        if (!isValidEmail(email.value)) {
            _error.value = "invalid_email"
            _showErrorDialog.value = true
            return
        }

        val idR = (1..9999).random().toLong()
        val user = User(
            id = idR,
            createDate = Date(),
            email = email.value,
            enabled = true,
            firstName = name.value,
            lastName = lastName.value,
            password = password.value,
            tokenExpired = false,
            roles = listOf(
                Role(
                    id = 1,
                    name = "STUDENT"
                )
            )
        )
        val formattedDate = birthDate.value?.let { convertMillisToDate(it) } ?: ""
        val student = Student(
           // user = user,
            id = idR,
            description = "Nuevo estudiante",
            premium = false,
            linkedinUrl = "",
            githubUrl = "" ,
            bornDate = formattedDate,
            location = "",

        )

        viewModelScope.launch {
            try {
                val result = repository.createStudent(student)
                if (result.isSuccess) {
                    _error.value = null
                    onSuccess()
                } else {
                    _error.value = "register_failed"
                }
            } catch (e: Exception) {
                _error.value = "network_error"
            }
        }
    }



}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}