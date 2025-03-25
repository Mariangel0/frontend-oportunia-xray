package com.frontend.oportunia.data.datasource.model

data class StudentDto(
    val id: UserDto,
    val description: String,
    val premium: Boolean,
    val linkedinUrl: String,
    val githubUrl: String
)