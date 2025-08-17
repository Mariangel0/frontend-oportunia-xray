package com.frontend.oportunia.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import com.frontend.oportunia.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> get() = _currentUser

    private val _loggedStudent = MutableStateFlow<Student?>(null)
    val loggedStudent: StateFlow<Student?> get() = _loggedStudent

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> get() = _profileImageUri

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

//    init {
//        loadProfile()
//    }

    fun loadProfile() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
                .onSuccess { user ->
                    _currentUser.value = user

                    val isStudent = user?.roles?.any { it.name == "USER" }
                    if (isStudent == true) {
                        studentRepository.findStudentByUserId(user.id!!)
                            .onSuccess { student ->
                                _loggedStudent.value = student
                            }
                            .onFailure {
                                Log.e("ProfileViewModel", "Error fetching student info", it)
                            }
                    } else {
                        _loggedStudent.value = null
                    }
                }
                .onFailure {
                    Log.e("ProfileViewModel", "Error fetching user", it)
                }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            studentRepository.updateStudent(student)
                .onSuccess { updated ->
                    _loggedStudent.value = updated
                }
                .onFailure { e ->
                    _error.value = "Error al actualizar el perfil"
                    Log.e("ProfileViewModel", "Error actualizando estudiante", e)
                }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            userRepository.updateUser(user)
                .onSuccess { updatedUser ->
                    _currentUser.value = updatedUser
                }
                .onFailure { e ->
                    _error.value = "Error al actualizar el usuario"
                    Log.e("ProfileViewModel", "Error actualizando usuario", e)
                }
        }
    }

}
