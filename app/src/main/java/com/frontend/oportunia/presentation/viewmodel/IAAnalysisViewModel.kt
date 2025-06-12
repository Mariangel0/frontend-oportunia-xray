package com.frontend.oportunia.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.IAAnalysis
import com.frontend.oportunia.domain.repository.IAAnalysisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AnalysisUiState {
    object Loading : AnalysisUiState()
    data class Success(val analysis: IAAnalysis) : AnalysisUiState()
    data class Error(val message: String) : AnalysisUiState()
}

sealed class AnalysisListUiState {
    object Loading : AnalysisListUiState()
    data class Success(val analyses: List<IAAnalysis>) : AnalysisListUiState()
    data class Error(val message: String) : AnalysisListUiState()
}

@HiltViewModel
class IAAnalysisViewModel @Inject constructor(
    private val repository: IAAnalysisRepository
) : ViewModel() {

    private val _analysisListState = MutableStateFlow<AnalysisListUiState>(AnalysisListUiState.Loading)
    val analysisListState: StateFlow<AnalysisListUiState> = _analysisListState.asStateFlow()

    private val _selectedAnalysisState = MutableStateFlow<AnalysisUiState>(AnalysisUiState.Loading)
    val selectedAnalysisState: StateFlow<AnalysisUiState> = _selectedAnalysisState.asStateFlow()

    // Keep these for backward compatibility if needed
    private val _analysisList = MutableStateFlow<List<IAAnalysis>>(emptyList())
    val analysisList: StateFlow<List<IAAnalysis>> = _analysisList.asStateFlow()

    private val _selectedAnalysis = MutableStateFlow<IAAnalysis?>(null)
    val selectedAnalysis: StateFlow<IAAnalysis?> = _selectedAnalysis.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    /**
     * Load all IA analyses with improved error handling and state management
     */
    fun loadAllAnalyses() {
        viewModelScope.launch {
            _analysisListState.value = AnalysisListUiState.Loading
            _isLoading.value = true
            _errorMessage.value = null

            try {
                repository.findAllIAAnalyses()
                    .onSuccess { analyses ->
                        _analysisListState.value = AnalysisListUiState.Success(analyses)
                        _analysisList.value = analyses
                        Log.d(TAG, "Successfully loaded ${analyses.size} analyses")
                    }
                    .onFailure { exception ->
                        val errorMsg = "Error al cargar los análisis: ${exception.message}"
                        _analysisListState.value = AnalysisListUiState.Error(errorMsg)
                        _errorMessage.value = errorMsg
                        Log.e(TAG, "Error loading IA analyses", exception)
                    }
            } catch (exception: Exception) {
                val errorMsg = "Error inesperado al cargar los análisis"
                _analysisListState.value = AnalysisListUiState.Error(errorMsg)
                _errorMessage.value = errorMsg
                Log.e(TAG, "Unexpected error loading IA analyses", exception)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Load a specific IA analysis by ID with improved error handling and state management
     */
    fun loadAnalysisById(id: Long) {
        viewModelScope.launch {
            _selectedAnalysisState.value = AnalysisUiState.Loading
            _isLoading.value = true
            _errorMessage.value = null

            try {
                repository.findIAAnalysisById(id)
                    .onSuccess { analysis ->
                        _selectedAnalysisState.value = AnalysisUiState.Success(analysis)
                        _selectedAnalysis.value = analysis
                        Log.d(TAG, "Successfully loaded analysis with id: $id")
                    }
                    .onFailure { exception ->
                        val errorMsg = "Error al cargar el análisis: ${exception.message}"
                        _selectedAnalysisState.value = AnalysisUiState.Error(errorMsg)
                        _errorMessage.value = errorMsg
                        Log.e(TAG, "Error loading IA analysis with id: $id", exception)
                    }
            } catch (exception: Exception) {
                val errorMsg = "Error inesperado al cargar el análisis"
                _selectedAnalysisState.value = AnalysisUiState.Error(errorMsg)
                _errorMessage.value = errorMsg
                Log.e(TAG, "Unexpected error loading IA analysis with id: $id", exception)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun refreshAnalysis(id: Long) {
        loadAnalysisById(id)
    }

    fun refreshAllAnalyses() {
        loadAllAnalyses()
    }


    fun clearError() {
        _errorMessage.value = null
    }

    fun clearSelectedAnalysis() {
        _selectedAnalysis.value = null
        _selectedAnalysisState.value = AnalysisUiState.Loading
    }

    companion object {
        private const val TAG = "IAAnalysisViewModel"
    }
}