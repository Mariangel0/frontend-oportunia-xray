package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.AdminDto

class AdminMapper {
    fun mapToDomain(dto: AdminDto): Admin = Admin(
        id = dto.id
    )

    fun mapToDto(domain: Admin): AdminDto = AdminDto(
        id = domain.id
    )
}