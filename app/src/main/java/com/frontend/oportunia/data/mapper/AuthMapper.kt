package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.auth.AuthRequestDto
import com.frontend.oportunia.data.remote.dto.auth.AuthResponseDto
import com.frontend.oportunia.domain.model.AuthResult
import com.frontend.oportunia.domain.model.Credentials

object AuthMapper {
    /**
     * Converts credentials to DTO
     */
    fun credentialsToDto(credentials: Credentials): AuthRequestDto {
        return AuthRequestDto(
            username = credentials.username,
            password = credentials.password
        )
    }

    /**
     * Converts authentication response DTO to domain model
     */
    fun dtoToAuthResult(dto: AuthResponseDto): AuthResult {
        return AuthResult(
            token = dto.token,
            userId = dto.userId
        )
    }
}