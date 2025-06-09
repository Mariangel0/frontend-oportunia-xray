package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.quiz.MCAnswerDto
import com.frontend.oportunia.data.remote.dto.quiz.MCEvaluationDto
import com.frontend.oportunia.data.remote.dto.quiz.MCQuestionDto
import com.frontend.oportunia.data.remote.dto.quiz.MCRequestDto
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface QuizService {

    @POST("api/ai/{userId}/quiz/generate")
    suspend fun generateQuiz(
        @Path("userId") userId: Long,
        @Body request: MCRequestDto
    ): MCQuestionDto

    @POST("api/ai/{userId}/quiz/evaluate")
    suspend fun evaluate(
        @Path("userId") userId: Long,
        @Body request: MCAnswerDto
    ): MCEvaluationDto

}