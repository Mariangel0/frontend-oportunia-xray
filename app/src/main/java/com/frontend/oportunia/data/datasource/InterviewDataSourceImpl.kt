package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.InterviewDto
import com.frontend.oportunia.data.mapper.InterviewMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InterviewDataSourceImpl()
//    private val interviewMapper: InterviewMapper
//) : InterviewDataSource {
//
//    override suspend fun getInterviews(): Flow<List<InterviewDto>> = flow {
//        val interviews = InterviewProvider.findAllInterviews()  // Simula la obtención de entrevistas
//        emit(interviews.map { interviewMapper.mapToDto(it) })
//    }
//
//    override suspend fun getInterviewById(id: Long): InterviewDto? = InterviewProvider.findInterviewById(id)?.let {
//        interviewMapper.mapToDto(it)
//    }
//
//    override suspend fun insertInterview(interviewDto: InterviewDto) {
//    }
//
//    override suspend fun updateInterview(interviewDto: InterviewDto) {
//    }
//
//    override suspend fun deleteInterview(interviewDto: InterviewDto) {
//    }
//}
