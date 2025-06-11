package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.EducationDto
import com.frontend.oportunia.domain.model.Education

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

    fun mapToDomainList(dtoList: List<EducationDto>): List<Education> = dtoList.map { mapToDomain(it) }



    fun mapToDto(domain: Education): EducationDto = EducationDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        name = domain.name,
        institution = domain.institution,
        year = domain.year
    )

    fun mapToDtoList(domainList: List<Education>): List<EducationDto> = domainList.map { mapToDto(it) }

}