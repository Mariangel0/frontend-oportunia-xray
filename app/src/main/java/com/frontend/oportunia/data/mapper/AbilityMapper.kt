package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Student
import javax.inject.Inject


class AbilityMapper @Inject constructor(
    private val studentMapper: StudentMapper
) {

    fun mapToDomain(dto: AbilityDto): Ability {
        return Ability(
            id = dto.id,
            studentId = studentMapper.mapToDomain(dto.studentId),
            name = dto.name
        )
    }

    fun mapToDomainList(abilityDto: List<AbilityDto>): List<Ability> {
        return abilityDto.map { mapToDomain(it) }
    }

    fun mapToDto(domain: Ability): AbilityDto {
        return AbilityDto(
                id = domain.id,
            studentId = studentMapper.mapToDto(domain.studentId),
            name = domain.name
        )
    }

    fun mapToDtoList(abilities: List<Ability>): List<AbilityDto> {
        return abilities.map { mapToDto(it) }
    }

}