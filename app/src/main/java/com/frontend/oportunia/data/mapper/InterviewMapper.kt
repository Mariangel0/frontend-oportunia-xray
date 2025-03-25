package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.InterviewDto

class InterviewMapper {
    fun mapToDomain(dto: InterviewDto): Interview = Interview(
        id = dto.id,
        studentId = dto.studentId,
        date = dto.date,
        result = dto.result
    )

    fun mapToDto(domain: Interview): InterviewDto = InterviewDto(
        id = domain.id,
        studentId = domain.studentId,
        date = domain.date,
        result = domain.result
    )
}