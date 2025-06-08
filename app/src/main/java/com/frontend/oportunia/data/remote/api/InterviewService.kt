package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.ChatResponseDto
import com.frontend.oportunia.data.remote.dto.InterviewDto
import com.frontend.oportunia.data.remote.dto.UserMessageDto
import com.frontend.oportunia.data.remote.dto.UserTextPromptDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterviewService {

    @POST("api/ai/{studentId}/interview")
    suspend fun sendPrompt(
        @Path("studentId") studentId: Long,
        @Body prompt: UserTextPromptDto
    ): ChatResponseDto

    @POST("api/ai/{studentId}/interview/continue")
    suspend fun continuePrompt(
        @Path("studentId") studentId: Long,
        @Body message: UserMessageDto
    ): ChatResponseDto

    @GET("interviews")
    suspend fun getAllInterviews(): Response<List<InterviewDto>>

    @GET("interviews/{id}")
    suspend fun getInterviewById(@Path("id") id: Long): Response<InterviewDto>

    @POST("interviews")
    suspend fun createInterview(@Body interview: InterviewDto): Response<InterviewDto>

    @DELETE("interviews/{id}")
    suspend fun deleteInterview(@Path("id") id: Long): Response<Unit>
}