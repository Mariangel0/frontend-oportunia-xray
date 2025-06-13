package com.frontend.oportunia.data.remote.dto

data class StudentDto(
    val id: Long?,
    val description: String?,
    val premium: Boolean?,
    val linkedinUrl: String?,
    val githubUrl: String?,
    val bornDate: String?,
    val location: String?,
    val user: UserDto?,
    val userId: Long? = null,
)

data class StudentRDto(
    val id: Long?,
    val user: UserRDto,
)
