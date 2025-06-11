package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.CompanyReview
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.CompanyReviewRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CompanyReviewViewModel @Inject constructor(
    private val reviewRepo: CompanyReviewRepository,
    private val authRepository: AuthRepository,
    private val studentRepository: StudentRepository
) : ViewModel() {

    // Campos del formulario
    val comment = MutableStateFlow("")
    val rating  = MutableStateFlow(0f)

    // Para mostrar errores simples
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Controla si debe mostrarse un diálogo de error
    private val _showErrorDialog = MutableStateFlow(false)
    val showErrorDialog: StateFlow<Boolean> get() = _showErrorDialog

    // Nombre de usuario logueado (para adjuntar al review si lo necesitas)
    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> get() = _username

    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> get() = _loggedUser

    private val _loggedStudent = MutableStateFlow<Student?>(null)
    val loggedStudent: StateFlow<Student?> get() = _loggedStudent


    init {
        getUser()
        getStudent()
    }

    fun getUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
                .onSuccess { _loggedUser.value = it }
                .onFailure { Log.e("StudentViewModel", "Error searching for user", it) }
        }
    }

    fun getStudent() {
        viewModelScope.launch {
            studentRepository.findStudentById(_loggedUser.value?.id ?: 1)
                .onSuccess { _loggedStudent.value = it }
                .onFailure { Log.e("StudentViewModel", "Error searching for student", it) }
        }
    }

    /** Limpia el mensaje de error */
    fun clearError() {
        _error.value = null
        _showErrorDialog.value = false
    }

    /** Valida el formulario y crea la reseña */
    fun submitReview(
        companyId: Long,
        onSuccess: () -> Unit
    ) {
        // Validaciones básicas
        if (comment.value.isBlank()) {
            _error.value = "comment_empty"
            _showErrorDialog.value = true
            return
        }
        if (rating.value <= 0f) {
            _error.value = "rating_empty"
            _showErrorDialog.value = true
            return
        }

        viewModelScope.launch {
            try {
                // Formateamos la fecha actual a String
                val createdAt = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss",
                    Locale.getDefault()
                ).format(Date())




                // Construimos el dominio CompanyReview
                val review = CompanyReview(
                    //id        = 0L,                           // el backend lo generará
                    comment   = comment.value,
                    rating    = rating.value,
                    createdAt = createdAt,
                    student   = Student( user = loggedUser.value),
                    company   = companyId
                )

                // Llamamos al repositorio
                val result = reviewRepo.createCompanyReview(review)
                if (result.isSuccess) {
                    _error.value = null
                    onSuccess()
                } else {
                    _error.value = "submit_failed"
                    _showErrorDialog.value = true
                }
            } catch (e: Exception) {
                Log.e("CompanyReviewViewModel", "Error submitting review", e)
                _error.value = "network_error"
                _showErrorDialog.value = true


            }
        }


    }
}
