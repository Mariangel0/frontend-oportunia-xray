package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.domain.model.Ability


class AbiltyMapper(
    private val studentMapper: StudentMapper
) {

    fun mapToDomain(dto: AbilityDto): Ability = Ability(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        name = dto.name
    )

    fun mapToDto(domain: Ability): AbilityDto = AbilityDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        name = domain.name
    )
}