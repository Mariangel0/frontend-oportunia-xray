package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.RoleDto
import com.frontend.oportunia.domain.model.Role

class RoleMapper {
    fun mapToDomain(dto: RoleDto): Role = Role(
        id = dto.id,
        name = dto.name
    )

    fun mapToDto(domain: Role): RoleDto = RoleDto(
        id = domain.id,
        name = domain.name
    )
}