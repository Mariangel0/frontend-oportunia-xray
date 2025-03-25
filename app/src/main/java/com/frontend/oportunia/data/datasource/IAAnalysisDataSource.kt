package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.IAAnalysisDto
import kotlinx.coroutines.flow.Flow

interface IAAnalysisDataSource {
    suspend fun getIAAnalyses(): Flow<List<IAAnalysisDto>>
    suspend fun getIAAnalysisById(id: Long): IAAnalysisDto?
    suspend fun insertIAAnalysis(iaAnalysisDto: IAAnalysisDto)
    suspend fun updateIAAnalysis(iaAnalysisDto: IAAnalysisDto)
    suspend fun deleteIAAnalysis(iaAnalysisDto: IAAnalysisDto)
}