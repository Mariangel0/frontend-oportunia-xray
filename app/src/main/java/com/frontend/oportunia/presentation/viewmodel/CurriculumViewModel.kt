package com.frontend.oportunia.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frontend.oportunia.domain.model.AnalyzedCVResponse
import com.frontend.oportunia.domain.model.Curriculum
import com.frontend.oportunia.domain.repository.CurriculumRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject



@HiltViewModel
class CurriculumViewModel @Inject constructor(
    private val repository: CurriculumRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState

    private val _analysisState = MutableStateFlow<AnalysisState>(AnalysisState.Idle)
    val analysisState: StateFlow<AnalysisState> = _analysisState

    fun uploadToTextract(fileUri: Uri, studentId: Long) {
        viewModelScope.launch {
            _uploadState.value = UploadState.Loading
            try {
                val inputStream = context.contentResolver.openInputStream(fileUri)
                    ?: throw Exception("No se pudo abrir el archivo")
                val bytes = inputStream.readBytes()

                val result = repository.uploadCurriculum(bytes, studentId).getOrThrow()
                _uploadState.value = UploadState.Success(result)
            } catch (e: Exception) {
                _uploadState.value = UploadState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }

    fun analyzeCurriculumWithIA(file: ByteArray, studentId: Long) {
        viewModelScope.launch {
            _analysisState.value = AnalysisState.Loading
            val result = repository.uploadCurriculumAI(file, studentId)
            _analysisState.value = result.fold(
                onSuccess = { AnalysisState.Success(it) },
                onFailure = { AnalysisState.Error(it.message ?: "Error desconocido") }
            )
        }
    }

    fun resetUploadState() {
        _uploadState.value = UploadState.Idle
    }

    fun resetAnalysisState() {
        _analysisState.value = AnalysisState.Idle
    }
}

sealed class UploadState {
    object Idle : UploadState()
    object Loading : UploadState()
    data class Success(val curriculum: Curriculum) : UploadState()
    data class Error(val message: String) : UploadState()
}

sealed class AnalysisState {
    object Idle : AnalysisState()
    object Loading : AnalysisState()
    data class Success(val response: AnalyzedCVResponse) : AnalysisState()
    data class Error(val message: String) : AnalysisState()
}