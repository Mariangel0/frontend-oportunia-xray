package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val loginViewModel: LoginViewModel
) : ViewModel() {

   // val student: StateFlow<Student?> = loginViewModel.loggedStudent

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> get() = _profileImageUri

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun updateProfileImage(uri: Uri) {
        _profileImageUri.value = uri
    }

//    fun saveProfileChanges(
//        description: String,
//        linkedinUrl: String,
//        githubUrl: String
//    ) {
//        val currentStudent = student.value ?: return
//
//        val updatedStudent = currentStudent.copy(
//            description = description,
//            linkedinUrl = linkedinUrl,
//            githubUrl = githubUrl
//        )
//
//        _isSaving.value = true
//        _error.value = null
//
//        viewModelScope.launch {
//            val result = studentRepository.updateStudent(updatedStudent)
//            if (result.isSuccess) {
//                loginViewModel.setLoggedStudent(updatedStudent) // ✅ actualizar estado global
//            } else {
//                _error.value = "No se pudo guardar el perfil"
//            }
//            _isSaving.value = false
//        }
//    }
}
