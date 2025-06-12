package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Interview
import com.frontend.oportunia.domain.model.InterviewChatResponse

interface InterviewRepository {
    suspend fun findInterviewById(interviewId: Long): Result<Interview>
    suspend fun findAllInterviews(): Result<List<Interview>>
    suspend fun startInterview(studentId: Long, message: String, jobPosition: String, typeOfInterview: String): Result<InterviewChatResponse>
    suspend fun continueInterview(studentId: Long, message: String): Result<InterviewChatResponse>
}