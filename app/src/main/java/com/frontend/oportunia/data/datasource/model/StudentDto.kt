package com.frontend.oportunia.data.datasource.model

data class StudentDto(
    val user: UserDto,
    val description: String,
    val premium: Boolean,
    val linkedinUrl: String,
    val githubUrl: String
)
