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
            timeline = dto.timeline,
            student = studentMapper . mapToDomain (dto.student),
            role = dto.role,
            company = dto.company,
        )
    }

    fun mapToDomainList(experienceDto: List<ExperienceDto>): List<Experience> {
        return experienceDto.map { mapToDomain(it) }
    }


    fun mapToDto(domain: Experience): ExperienceDto {
        return ExperienceDto(
            id = domain.id,
            timeline = domain.timeline,
            role = domain.role,
            student = studentMapper.mapToDto(domain.student),
            company = domain.company,
        )
    }

    fun mapToDtoList(experiences: List<Experience>): List<ExperienceDto> {
        return experiences.map { mapToDto(it) }
    }
}