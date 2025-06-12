package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.InterviewDataSource
import com.frontend.oportunia.data.mapper.InterviewMapper
import com.frontend.oportunia.data.remote.dto.UserMessageDto
import com.frontend.oportunia.data.remote.dto.UserTextPromptDto
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.ChatResponse
import com.frontend.oportunia.domain.model.Interview
import com.frontend.oportunia.domain.model.InterviewChatResponse
import com.frontend.oportunia.domain.repository.InterviewRepository
import java.io.IOException
import javax.inject.Inject

class InterviewRepositoryImpl @Inject constructor(
    private val dataSource: InterviewDataSource,
    private val interviewMapper: InterviewMapper
) : InterviewRepository {

    companion object {
        private const val TAG = "InterviewRepository"
    }

    override suspend fun startInterview(
        studentId: Long,
        message: String,
        jobPosition: String,
        typeOfInterview: String
    ): Result<InterviewChatResponse> = runCatching {
        val prompt = UserTextPromptDto(
            id = studentId,
            message = message,
            jobPosition = jobPosition,
            typeOfInterview = typeOfInterview
        )
        val response = dataSource.startInterview(studentId, prompt)
        interviewMapper.mapToDomain(response)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to start interview", throwable)
        throwDomainError(throwable, "start interview")
    }

    override suspend fun continueInterview(
        studentId: Long,
        message: String
    ): Result<InterviewChatResponse> = runCatching {
        val input = UserMessageDto(
            id = studentId,
            message = message
        )
        val response = dataSource.continueInterview(studentId, input)
        interviewMapper.mapToDomain(response)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to continue interview", throwable)
        throwDomainError(throwable, "continue interview")
    }

    override suspend fun findAllInterviews(): Result<List<Interview>> = runCatching {
        dataSource.getAllInterviews().getOrThrow().map {
            interviewMapper.mapToDomain(it)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch interviews", throwable)
        throwDomainError(throwable, "fetch interviews")
    }

    override suspend fun findInterviewById(interviewId: Long): Result<Interview> = runCatching {
        val dto = dataSource.getInterviewById(interviewId).getOrThrow()
        interviewMapper.mapToDomain(dto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch interview by ID", throwable)
        throwDomainError(throwable, "fetch interview")
    }

    private fun throwDomainError(throwable: Throwable, context: String): Nothing =
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to $context")
            is IllegalArgumentException -> throw DomainError.MappingError("Mapping error during $context")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("Unknown error during $context")
        }
}
