package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.ExperienceDto
import com.frontend.oportunia.domain.model.Experience

class ExperienceMapper(
    private val studentMapper: StudentMapper
) {

    fun mapToDomain(dto: ExperienceDto): Experience = Experience(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        company = dto.company,
        role = dto.role,
        year = dto.year
    )

    fun mapToDto(domain: Experience): ExperienceDto = ExperienceDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        company = domain.company,
        role = domain.role,
        year = domain.year
    )
}