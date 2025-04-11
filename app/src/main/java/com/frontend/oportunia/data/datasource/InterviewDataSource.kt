package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.InterviewDto
import kotlinx.coroutines.flow.Flow

interface InterviewDataSource {
    suspend fun getInterviews(): Flow<List<InterviewDto>>
    suspend fun getInterviewById(id: Long): InterviewDto?
    suspend fun insertInterview(interviewDto: InterviewDto)
    suspend fun updateInterview(interviewDto: InterviewDto)
    suspend fun deleteInterview(interviewDto: InterviewDto)
}