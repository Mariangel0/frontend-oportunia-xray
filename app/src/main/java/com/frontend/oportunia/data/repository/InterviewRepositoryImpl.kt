package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.InterviewDataSource
import com.frontend.oportunia.data.mapper.InterviewMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class InterviewRepositoryImpl(
    private val dataSource: InterviewDataSource,
    private val interviewMapper: InterviewMapper
) : InterviewRepository {

    companion object {
        private const val TAG = "InterviewRepository"
    }

    override suspend fun findAllInterviews(): Result<List<Interview>> = runCatching {
        dataSource.getInterviews().first().map { interviewDto ->
            interviewMapper.mapToDomain(interviewDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch interviews", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch interviews")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping interviews")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findInterviewById(interviewId: Long): Result<Interview> = runCatching {
        val interviewDto = dataSource.getInterviewById(interviewId) ?: throw DomainError.InterviewError("Interview not found")
        interviewMapper.mapToDomain(interviewDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch interview with ID: $interviewId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch interview")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping interview")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
