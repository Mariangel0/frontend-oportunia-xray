package com.frontend.oportunia.data.remote.dto

data class ExperienceDto(
    val id: Long,
    val studentId: StudentDto,
    val company: CompanyDto,
    val role: String,
    val year: Int
)