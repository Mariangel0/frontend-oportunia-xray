package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.UserDto
import com.frontend.oportunia.domain.model.User

class UserMapper {
    fun mapToDomain(dto: UserDto): User = User(
        id = dto.id,
        createDate = dto.createDate,
        email = dto.email,
        enabled = dto.enabled,
        firstName = dto.firstName,
        lastName = dto.lastName,
        password = dto.password,
        tokenExpired = dto.tokenExpired
    )

    fun mapToDto(domain: User): UserDto = UserDto(
        id = domain.id,
        createDate = domain.createDate,
        email = domain.email,
        enabled = domain.enabled,
        firstName = domain.firstName,
        lastName = domain.lastName,
        password = domain.password,
        tokenExpired = domain.tokenExpired
    )
}