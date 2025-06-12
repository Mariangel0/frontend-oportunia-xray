package com.frontend.oportunia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Streak
import com.frontend.oportunia.domain.repository.StreakRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val streakRepository: StreakRepository,
    private val studentRepository: StudentRepository
) : ViewModel() {

    private val _hasCompletedToday = MutableStateFlow(false)
    val hasCompletedToday: StateFlow<Boolean> = _hasCompletedToday

    fun loadStreak(userId: Long) {
        viewModelScope.launch {
            studentRepository.findStudentByUserId(userId).onSuccess { student ->
                val studentId = student.id!!
                streakRepository.getStreak(studentId).onSuccess { streak ->
                    val systemZone = java.time.ZoneId.systemDefault()
                    val today = java.time.LocalDate.now(systemZone)

                    val lastActivityDate = streak.lastActivity?.toInstant()
                        ?.atZone(systemZone)
                        ?.toLocalDate()

                    _hasCompletedToday.value = lastActivityDate?.isEqual(today) == true
                }.onFailure {
                    // ⚠️ Si falla porque no existe, lo crea
                    streakRepository.createStreak(
                        Streak(
                            id = null,
                            days = 0,
                            bestStreak = 0,
                            lastActivity = Date(0),
                            student = student
                        )
                    )
                    _hasCompletedToday.value = false
                }
            }.onFailure {
                _hasCompletedToday.value = false
            }
        }
    }
}

