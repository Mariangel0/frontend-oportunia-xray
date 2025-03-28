package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Interview

interface InterviewRepository {
    suspend fun findInterviewById(interviewId: Long): Result<Interview>
    suspend fun findAllInterviews(): Result<List<Interview>>
}