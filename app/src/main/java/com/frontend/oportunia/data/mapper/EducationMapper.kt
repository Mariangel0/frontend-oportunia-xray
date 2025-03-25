package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.EducationDto

class EducationMapper {
    fun mapToDomain(dto: EducationDto): Education = Education(
        id = dto.id,
        studentId = dto.studentId,
        name = dto.name,
        institution = dto.institution,
        year = dto.year
    )

    fun mapToDto(domain: Education): EducationDto = EducationDto(
        id = domain.id,
        studentId = domain.studentId,
        name = domain.name,
        institution = domain.institution,
        year = domain.year
    )
}