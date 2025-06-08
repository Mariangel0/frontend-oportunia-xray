package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.ChatResponse
import com.frontend.oportunia.domain.model.Interview

interface InterviewRepository {
    suspend fun findInterviewById(interviewId: Long): Result<Interview>
    suspend fun findAllInterviews(): Result<List<Interview>>
    suspend fun startInterview(studentId: Long, message: String, jobPosition: String, typeOfInterview: String): Result<ChatResponse>
    suspend fun continueInterview(studentId: Long, message: String): Result<ChatResponse>
}