package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.EducationDto

class EducationMapper(
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: EducationDto): Education = Education(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        name = dto.name,
        institution = dto.institution,
        year = dto.year
    )

    fun mapToDto(domain: Education): EducationDto = EducationDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        name = domain.name,
        institution = domain.institution,
        year = domain.year
    )
}