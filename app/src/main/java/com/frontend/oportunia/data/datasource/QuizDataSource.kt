package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.QuizService
import com.frontend.oportunia.data.remote.dto.quiz.MCAnswerDto
import com.frontend.oportunia.data.remote.dto.quiz.MCEvaluationDto
import com.frontend.oportunia.data.remote.dto.quiz.MCQuestionDto
import com.frontend.oportunia.data.remote.dto.quiz.MCRequestDto
import javax.inject.Inject

class QuizDataSource @Inject constructor(
    private val quizService: QuizService
) {

    suspend fun generateQuiz(userId: Long, request: MCRequestDto): Result<MCQuestionDto> = safeApiCall {
        quizService.generateQuiz(userId, request)
    }

    suspend fun evaluateAnswer(userId: Long, answer: MCAnswerDto): Result<MCEvaluationDto> = safeApiCall {
        quizService.evaluate(userId, answer)
    }

    suspend fun markQuizCompleted(userId: Long): Result<Unit> = safeApiCall {
        quizService.markQuizCompleted(userId)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): Result<T> = try {
        val result = apiCall()
        Result.success(result)
    } catch (e: Exception) {
        Result.failure(e)
    }

}
