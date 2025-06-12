package com.frontend.oportunia.data.remote.dto

data class ExperienceDto(
    val id: Long?,
    val timeline: String,
    val studentId: StudentDto,
    val company: String,
    val role: String
)