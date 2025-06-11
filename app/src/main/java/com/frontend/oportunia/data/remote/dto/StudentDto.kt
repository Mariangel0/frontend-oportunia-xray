package com.frontend.oportunia.data.remote.dto

data class StudentDto(
    val user: UserDto?,
    val description: String?,
    val premium: Boolean?,
    val linkedinUrl: String?,
    val githubUrl: String?,
    val bornDate: String?,
    val location: String?,
    val userId : Long?
)

data class StudentRDto(
    val user: UserRDto,
)
