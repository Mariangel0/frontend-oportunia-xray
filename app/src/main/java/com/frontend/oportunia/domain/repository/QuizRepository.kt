package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.MCAnswer
import com.frontend.oportunia.domain.model.MCQuestion
import com.frontend.oportunia.domain.model.MCEvaluation
import com.frontend.oportunia.domain.model.MCRequest
import java.util.Date

interface QuizRepository {
    suspend fun generateQuiz(userId: Long, request: MCRequest): Result<MCQuestion>
    suspend fun evaluateAnswer(userId: Long, answer: MCAnswer): Result<MCEvaluation>
    suspend fun markQuizCompleted(userId: Long): Result<Unit>
}
