package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.QuizDataSource
import com.frontend.oportunia.data.mapper.QuizMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.*
import com.frontend.oportunia.domain.repository.QuizRepository
import java.io.IOException
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val dataSource: QuizDataSource,
    private val mapper: QuizMapper
) : QuizRepository {

    companion object {
        private const val TAG = "QuizRepository"
    }

    override suspend fun generateQuiz(userId: Long, request: MCRequest): Result<MCQuestion> = runCatching {
        val dtoRequest = mapper.mapToDto(request)
        val response = dataSource.generateQuiz(userId, dtoRequest).getOrThrow()
        mapper.mapToDomain(response)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to generate quiz", throwable)
        throwDomainError(throwable, "generate quiz")
    }

    override suspend fun evaluateAnswer(userId: Long, answer: MCAnswer): Result<MCEvaluation> = runCatching {
        val dto = mapper.mapToDto(answer)
        val response = dataSource.evaluateAnswer(userId, dto).getOrThrow()
        mapper.mapToDomain(response)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to evaluate quiz answer", throwable)
        throwDomainError(throwable, "evaluate quiz")
    }

    override suspend fun markQuizCompleted(userId: Long): Result<Unit> = runCatching {
        dataSource.markQuizCompleted(userId).getOrThrow()
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to mark quiz as completed", throwable)
        throwDomainError(throwable, "mark quiz as completed")
    }

    private fun throwDomainError(throwable: Throwable, context: String): Nothing =
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to $context")
            is IllegalArgumentException -> throw DomainError.MappingError("Mapping error during $context")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError("Unknown error during $context")
        }
}
