package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.AdminDto

class AdminMapper(
    private val userMapper: UserMapper
) {
    fun mapToDomain(dto: AdminDto): Admin = Admin(
        id = userMapper.mapToDomain(dto.id)
    )

    fun mapToDto(domain: Admin): AdminDto = AdminDto(
        id = userMapper.mapToDto(domain.id)
    )
}