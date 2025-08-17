package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.IAAnalysisDataSource
import com.frontend.oportunia.data.mapper.IAAnalysisMapper
import com.frontend.oportunia.data.repository.AdminRepositoryImpl.Companion
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Admin
import com.frontend.oportunia.domain.model.IAAnalysis
import com.frontend.oportunia.domain.model.Student
import com.frontend.oportunia.domain.repository.IAAnalysisRepository
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class IAAnalysisRepositoryImpl @Inject constructor(
    private val dataSource: IAAnalysisDataSource,
    private val iaAnalysisMapper: IAAnalysisMapper
) : IAAnalysisRepository {

    companion object {
        private const val TAG = "IAAnalysisRepository"
    }

    override suspend fun findAllIAAnalyses(): Result<List<IAAnalysis>> {
        return try {
            dataSource.getIAAnalyses().map { analysesDtos ->
                iaAnalysisMapper.mapToDomainList(analysesDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching analyses: ${e.message}"))
        }
    }

    override suspend fun findIAAnalysisById(iaAnalysisId: Long): Result<IAAnalysis> =
        dataSource.getIAAnalysisById(iaAnalysisId).map { studentDto ->
            iaAnalysisMapper.mapToDomain(studentDto)
        }


}
