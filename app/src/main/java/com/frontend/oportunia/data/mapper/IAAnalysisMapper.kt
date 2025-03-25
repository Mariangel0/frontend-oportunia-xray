package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.IAAnalysisDto

class IAAnalysisMapper {
    fun mapToDomain(dto: IAAnalysisDto): IAAnalysis = IAAnalysis(
        id = dto.id,
        interviewId = dto.interviewId,
        curriculumId = dto.curriculumId,
        recommendations = dto.recommendations,
        score = dto.score,
        date = dto.date
    )

    fun mapToDto(domain: IAAnalysis): IAAnalysisDto = IAAnalysisDto(
        id = domain.id,
        interviewId = domain.interviewId,
        curriculumId = domain.curriculumId,
        recommendations = domain.recommendations,
        score = domain.score,
        date = domain.date
    )
}