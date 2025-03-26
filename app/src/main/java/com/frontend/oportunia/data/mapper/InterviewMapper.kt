package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.InterviewDto

class InterviewMapper(
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: InterviewDto): Interview = Interview(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        date = dto.date,
        result = dto.result
    )

    fun mapToDto(domain: Interview): InterviewDto = InterviewDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        date = domain.date,
        result = domain.result
    )
}