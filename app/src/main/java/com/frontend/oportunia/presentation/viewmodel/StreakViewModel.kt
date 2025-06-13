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

    private val _currentStreak = MutableStateFlow(0)
    val currentStreak: StateFlow<Int> = _currentStreak

    private val _lastActivityDate = MutableStateFlow<LocalDate?>(null)
    val lastActivityDate: StateFlow<LocalDate?> = _lastActivityDate

    fun loadStreak(userId: Long) {
        viewModelScope.launch {
            studentRepository.findStudentByUserId(userId).onSuccess { student ->
                val studentId = student.id!!
                streakRepository.getStreak(studentId).onSuccess { streak ->
                    val systemZone = ZoneId.systemDefault()
                    val today = LocalDate.now(systemZone)

                    val lastActivityDate = streak.lastActivity?.toInstant()
                        ?.atZone(systemZone)
                        ?.toLocalDate()

                    _hasCompletedToday.value = lastActivityDate?.isEqual(today) == true
                    _currentStreak.value = streak.days
                    _lastActivityDate.value = lastActivityDate
                }.onFailure {
                    _hasCompletedToday.value = false
                    _currentStreak.value = 0
                    _lastActivityDate.value = null
                }
            }.onFailure {
                _hasCompletedToday.value = false
                _currentStreak.value = 0
                _lastActivityDate.value = null
            }
        }
    }

    fun getCompletedDaysInWeek(startOfWeek: LocalDate): List<LocalDate> {
        val lastActivity = _lastActivityDate.value ?: return emptyList()
        val streak = _currentStreak.value

        if (streak == 0) return emptyList()

        val completedDays = mutableListOf<LocalDate>()

        // Generar los días completados basado en la racha
        for (i in 0 until streak) {
            val completedDay = lastActivity.minusDays(i.toLong())
            // Solo incluir días que estén en la semana actual (lunes a viernes)
            if (completedDay >= startOfWeek && completedDay <= startOfWeek.plusDays(4)) {
                completedDays.add(completedDay)
            }
        }

        return completedDays
    }
}

