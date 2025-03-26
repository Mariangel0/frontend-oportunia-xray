package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.PrivilegeDto

class PrivilegeMapper {
    fun mapToDomain(dto: PrivilegeDto): Privilege = Privilege(
        id = dto.id,
        name = dto.name
    )

    fun mapToDto(domain: Privilege): PrivilegeDto = PrivilegeDto(
        id = domain.id,
        name = domain.name
    )
}