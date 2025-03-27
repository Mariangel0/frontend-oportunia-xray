package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.IAAnalysisDataSource
import com.frontend.oportunia.data.mapper.IAAnalysisMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class IAAnalysisRepositoryImpl(
    private val dataSource: IAAnalysisDataSource,
    private val iaAnalysisMapper: IAAnalysisMapper
) : IAAnalysisRepository {

    companion object {
        private const val TAG = "IAAnalysisRepository"
    }

    override suspend fun findAllIAAnalyses(): Result<List<IAAnalysis>> = runCatching {
        dataSource.getIAAnalyses().first().map { iaAnalysisDto ->
            iaAnalysisMapper.mapToDomain(iaAnalysisDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch IA analyses", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch IA analyses")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping IA analyses")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findIAAnalysisById(iaAnalysisId: Long): Result<IAAnalysis> = runCatching {
        val iaAnalysisDto = dataSource.getIAAnalysisById(iaAnalysisId) ?: throw DomainError.IAAnalysisError("IA Analysis not found")
        iaAnalysisMapper.mapToDomain(iaAnalysisDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch IA Analysis with ID: $iaAnalysisId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch IA Analysis")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping IA Analysis")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
