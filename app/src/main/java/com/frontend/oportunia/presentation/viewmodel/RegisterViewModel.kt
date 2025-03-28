package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegisterViewModel : ViewModel() {

    // Datos personales
    val name = MutableStateFlow("")
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

    fun register() { // Pruebas
        if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank() || birthDate.value == null || education.value.isEmpty() || company.value.isBlank() || jobPosition.value.isBlank() ) {
            _error.value = "empty_fields"
            return
        }

        if (password.value != confirmPassword.value) {
            _error.value = "password_mismatch"
            return
        }

        // Simulación de registro
        println("Registro exitoso con nombre: ${name.value}")
        _error.value = null
    }

    fun clearError() {
        _error.value = null
    }
}
