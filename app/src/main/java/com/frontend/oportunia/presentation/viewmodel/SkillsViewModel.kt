package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.data.local.AuthPreferences
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.domain.repository.AbilityRepository
import com.frontend.oportunia.domain.repository.AuthRepository
import com.frontend.oportunia.domain.repository.ExperienceRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val abilityRepository: AbilityRepository,
    private val experienceRepository: ExperienceRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        getUser()
    }

    private val _abilityList = MutableStateFlow<List<Ability>>(emptyList())
    val abilityList: StateFlow<List<Ability>> = _abilityList

    private val _experienceList = MutableStateFlow<List<Experience>>(emptyList())
    val experienceList: StateFlow<List<Experience>> = _experienceList

    private val _loggedStudent = MutableStateFlow<Student?>(null)
    val loggedStudent: StateFlow<Student?> get() = _loggedStudent

    private val _loggedUser = MutableStateFlow<User?>(null)
    val loggedUser: StateFlow<User?> get() = _loggedUser

    fun getAbilitiesForStudent(studentId: Long) {
        viewModelScope.launch {
            abilityRepository.findAbilitiesByStudentId(studentId)
                .onSuccess { _abilityList.value = it }
                .onFailure { Log.e("StudentViewModel", "Error searching for ability with student id: $studentId", it) }
        }
    }

    fun getExperiencesForStudent(studentId: Long) {
        viewModelScope.launch {
            experienceRepository.findExperiencesByStudentId(studentId)
                .onSuccess { _experienceList.value = it }
                .onFailure { Log.e("StudentViewModel", "Error searching for experience with student id: $studentId", it) }
        }
    }

    fun getStudentById(studentId: Long) {
        viewModelScope.launch {
            studentRepository.findStudentByUserId(studentId)
                .onSuccess { _loggedStudent.value = it }
                .onFailure { Log.e("StudentViewModel", "Error searching for student with id: $studentId", it) }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
                .onSuccess { _loggedUser.value = it }
                .onFailure { Log.e("StudentViewModel", "Error searching for user", it) }
        }
    }

}