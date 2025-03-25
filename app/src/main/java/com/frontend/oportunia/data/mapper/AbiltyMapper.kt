package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.AbilityDto


class AbiltyMapper {

    fun mapToDomain(dto: AbilityDto): Ability = Ability(
        id = dto.id,
        studentId = dto.studentId,
        name = dto.name
    )

    fun mapToDto(domain: Ability): AbilityDto = AbilityDto(
        id = domain.id,
        studentId = domain.studentId,
        name = domain.name
    )
}