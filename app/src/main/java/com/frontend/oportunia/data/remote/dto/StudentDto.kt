package com.frontend.oportunia.data.remote.dto

data class StudentDto(
   // val user: UserDto,
    val id: Long,
    val description: String,
    val premium: Boolean,
    val linkedinUrl: String,
    val githubUrl: String,
    val bornDate: String,
    val location: String
)
