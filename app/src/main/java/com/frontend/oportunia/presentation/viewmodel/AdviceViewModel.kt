package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.Advice
import com.frontend.oportunia.domain.repository.AdviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdviceViewModel @Inject constructor (
    private val repository: AdviceRepository
) : ViewModel() {

    private val _adviceList = MutableStateFlow<List<Advice>>(emptyList())
    val adviceList: StateFlow<List<Advice>> = _adviceList

    private val _selectedAdvice = MutableStateFlow<Advice?>(null)
    val selectedAdvice: StateFlow<Advice?> = _selectedAdvice

    fun loadAllAdvices() {
        viewModelScope.launch {
            repository.findAllAdvices()
                .onSuccess { _adviceList.value = it }
                .onFailure { Log.e("AdviceViewModel", "Error loading advices", it) }
        }
    }

    fun loadAdviceById(id: Long) {
        viewModelScope.launch {
            repository.findAdviceById(id)
                .onSuccess { _selectedAdvice.value = it }
                .onFailure { Log.e("AdviceViewModel", "Error loading advice with id: $id", it) }
        }
    }

}