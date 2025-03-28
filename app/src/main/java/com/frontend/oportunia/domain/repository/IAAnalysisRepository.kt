package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.IAAnalysis

interface IAAnalysisRepository {
    suspend fun findAllIAAnalyses(): Result<List<IAAnalysis>>
    suspend fun findIAAnalysisById(iaAnalysisId: Long): Result<IAAnalysis>
}