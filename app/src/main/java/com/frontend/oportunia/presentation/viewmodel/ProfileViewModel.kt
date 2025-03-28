package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _student = MutableStateFlow<Student?>(null)
    val student: StateFlow<Student?> = _student

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    fun loadStudentProfile(studentId: Long) {
        viewModelScope.launch {
            studentRepository.findStudentById(studentId)
                .onSuccess { _student.value = it }
                .onFailure { Log.e("ProfileViewModel", "Error loading student profile", it) }
        }
    }

    fun updateProfileImage(uri: Uri) {
        _profileImageUri.value = uri
    }
}
