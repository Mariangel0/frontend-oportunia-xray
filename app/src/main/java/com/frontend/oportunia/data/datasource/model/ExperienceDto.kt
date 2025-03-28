package com.frontend.oportunia.data.datasource.model

data class ExperienceDto(
    val id: Long,
    val studentId: StudentDto,
    val company: Long,
    val role: String,
    val year: Int
)