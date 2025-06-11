package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.api.InterviewService
import com.frontend.oportunia.data.remote.dto.ChatResponseDto
import com.frontend.oportunia.data.remote.dto.InterviewDto
import com.frontend.oportunia.data.remote.dto.UserMessageDto
import com.frontend.oportunia.data.remote.dto.UserTextPromptDto
import retrofit2.Response
import javax.inject.Inject

class InterviewDataSource @Inject constructor(
    private val interviewService: InterviewService
) {

    suspend fun startInterview(studentId: Long, prompt: UserTextPromptDto): ChatResponseDto {
        return interviewService.sendPrompt(studentId, prompt)
    }

    suspend fun continueInterview(studentId: Long, message: UserMessageDto): ChatResponseDto {
        return interviewService.continuePrompt(studentId, message)
    }

    suspend fun getAllInterviews(): Result<List<InterviewDto>> = safeApiCall {
        interviewService.getAllInterviews()
    }

    suspend fun getInterviewById(id: Long): Result<InterviewDto> = safeApiCall {
        interviewService.getInterviewById(id)
    }

    suspend fun createInterview(interviewDto: InterviewDto): Response<InterviewDto> {
        return interviewService.createInterview(interviewDto)
    }

    suspend fun deleteInterview(id: Long): Response<Unit> {
        return interviewService.deleteInterview(id)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
