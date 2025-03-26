package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.IAAnalysisDto

class IAAnalysisMapper(
    private val interviewMapper: InterviewMapper,
    private val curriculumMapper: CurriculumMapper
) {
    fun mapToDomain(dto: IAAnalysisDto): IAAnalysis = IAAnalysis(
        id = dto.id,
        interviewId = interviewMapper.mapToDomain(dto.interviewId),
        curriculumId = curriculumMapper.mapToDomain(dto.curriculumId),
        recommendations = dto.recommendations,
        score = dto.score,
        date = dto.date
    )

    fun mapToDto(domain: IAAnalysis): IAAnalysisDto = IAAnalysisDto(
        id = domain.id,
        interviewId = interviewMapper.mapToDto(domain.interviewId),
        curriculumId = curriculumMapper.mapToDto(domain.curriculumId),
        recommendations = domain.recommendations,
        score = domain.score,
        date = domain.date
    )
}