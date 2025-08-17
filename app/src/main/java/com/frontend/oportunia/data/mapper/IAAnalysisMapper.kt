package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.IAAnalysisDto
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.IAAnalysis
import javax.inject.Inject

class IAAnalysisMapper @Inject constructor(
    private val interviewMapper: InterviewMapper,
) {
    fun mapToDomain(dto: IAAnalysisDto): IAAnalysis = IAAnalysis(
        id = dto.id,
        recommendation = dto.recommendation,
        comment = dto.comment,
        score = dto.score,
        date = dto.date,
        interview = interviewMapper.mapToDomain(dto.interview),
    )

    fun mapToDto(domain: IAAnalysis): IAAnalysisDto = IAAnalysisDto(
        id = domain.id,
        recommendation = domain.recommendation,
        comment = domain.comment,
        score = domain.score,
        date = domain.date,
        interview = interviewMapper.mapToDto(domain.interview),
    )

    fun mapToDomainList(analysisDto: List<IAAnalysisDto>): List<IAAnalysis> {
        return analysisDto.map { mapToDomain(it) }
    }
}