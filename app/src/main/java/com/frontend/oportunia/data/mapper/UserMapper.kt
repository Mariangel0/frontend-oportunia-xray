package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.UserDto
import com.frontend.oportunia.data.remote.dto.UserRDto
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor(){
    fun mapToDomain(dto: UserDto): User {
        return User(
            id = dto.id,
            createDate = dto.createDate,
            email = dto.email,
            enabled = dto.enabled,
            firstName = dto.firstName,
            lastName = dto.lastName,
            password = dto.password,
            tokenExpired = dto.tokenExpired,
            roles = dto.roles?.map { RoleMapper().mapToDomain(it) }
        )
        
    }

    fun mapToDomainRDto(dto: UserRDto): User {
        return User(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName
        )
    }

    fun mapToDomainList(userDto: List<UserDto>): List<User> {
        return userDto.map { mapToDomain(it) }
    }

    fun mapToDto(domain: User): UserDto{
        return UserDto(
            id = domain.id,
            createDate = domain.createDate,
            email = domain.email,
            enabled = domain.enabled,
            firstName = domain.firstName,
            lastName = domain.lastName,
            password = domain.password,
            tokenExpired = domain.tokenExpired,
            roles = domain.roles?.map { RoleMapper().mapToDto(it) }
        )
    }

    fun mapToDtoRDto(domain: User): UserRDto {
        return UserRDto(
            id = domain.id,
            firstName = domain.firstName,
            lastName = domain.lastName
        )
    }

    fun mapToDtoList(users: List<User>): List<UserDto> {
        return users.map { mapToDto(it) }
    }



}