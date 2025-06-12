package com.frontend.oportunia.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.repository.StreakRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreakViewModel @Inject constructor(
    private val streakRepository: StreakRepository
) : ViewModel() {

    private val _hasCompletedToday = MutableStateFlow<Boolean>(false)
    val hasCompletedToday: StateFlow<Boolean> = _hasCompletedToday

    fun loadStreak(studentId: Long) {
        viewModelScope.launch {
            streakRepository.getStreak(studentId).onSuccess { streak ->
                val today = java.time.LocalDate.now()
                val lastActivity = streak.lastActivity.toInstant()?.atZone(java.time.ZoneId.systemDefault())?.toLocalDate()

                _hasCompletedToday.value = lastActivity == today
            }.onFailure {
                _hasCompletedToday.value = false
            }
        }
    }
}
