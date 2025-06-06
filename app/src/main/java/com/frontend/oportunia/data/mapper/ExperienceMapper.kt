package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.ExperienceDto
import com.frontend.oportunia.domain.model.Experience
import javax.inject.Inject

class ExperienceMapper @Inject constructor (
    private val studentMapper: StudentMapper,
    private val companyMapper: CompanyMapper
) {

    fun mapToDomain(dto: ExperienceDto): Experience {
        return Experience(
            id = dto.id,
            studentId = studentMapper . mapToDomain (dto.studentId),
            company = dto.company,
            role = dto.role,
            timeline = dto.timeline
        )
    }

    fun mapToDomainList(experienceDto: List<ExperienceDto>): List<Experience> {
        return experienceDto.map { mapToDomain(it) }
    }


    fun mapToDto(domain: Experience): ExperienceDto {
        return ExperienceDto(
            id = domain.id,
            studentId = studentMapper.mapToDto(domain.studentId),
            company = domain.company,
            role = domain.role,
            timeline = domain.timeline
        )
    }

    fun mapToDtoList(experiences: List<Experience>): List<ExperienceDto> {
        return experiences.map { mapToDto(it) }
    }
}