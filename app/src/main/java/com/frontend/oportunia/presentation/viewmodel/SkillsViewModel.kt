package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.repository.AbilityRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val studentRepository: StudentRepository,
    private val abilityRepository: AbilityRepository
) : ViewModel() {

    private val _abilityList = MutableStateFlow<List<Ability>>(emptyList())
    val abilityList: StateFlow<List<Ability>> = _abilityList

    private val _loggedStudent = MutableStateFlow<Student?>(null)
    val loggedStudent: StateFlow<Student?> get() = _loggedStudent

    fun getAbilitiesForStudent(studentId: Long) {
        viewModelScope.launch {
            abilityRepository.findAbilitiesByStudentId(studentId)
                .onSuccess { _abilityList.value = it }
                .onFailure { Log.e("CompanyViewModel", "Error searching for company with name: $studentId", it) }
        }
    }

    fun getStudentById(studentId: Long) {

        viewModelScope.launch {
            studentRepository.findStudentByUserId(studentId)
                .onSuccess { _loggedStudent.value = it }
                .onFailure { Log.e("CompanyViewModel", "Error searching for company with name: $studentId", it) }
        }
    }
}