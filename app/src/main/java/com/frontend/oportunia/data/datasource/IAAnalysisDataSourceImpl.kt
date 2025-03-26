package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.IAAnalysisDto
import com.frontend.oportunia.data.mapper.IAAnalysisMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IAAnalysisDataSourceImpl(
    private val iaAnalysisMapper: IAAnalysisMapper
) : IAAnalysisDataSource {

    override suspend fun getIAAnalyses(): Flow<List<IAAnalysisDto>> = flow {
        val analyses = IAAnalysisProvider.findAllIAAnalyses()  // Simula la obtención de análisis IA
        emit(analyses.map { iaAnalysisMapper.mapToDto(it) })
    }

    override suspend fun getIAAnalysisById(id: Long): IAAnalysisDto? = IAAnalysisProvider.findIAAnalysisById(id)?.let {
        iaAnalysisMapper.mapToDto(it)
    }

    override suspend fun insertIAAnalysis(iaAnalysisDto: IAAnalysisDto) {
    }

    override suspend fun updateIAAnalysis(iaAnalysisDto: IAAnalysisDto) {
    }

    override suspend fun deleteIAAnalysis(iaAnalysisDto: IAAnalysisDto) {
    }
}