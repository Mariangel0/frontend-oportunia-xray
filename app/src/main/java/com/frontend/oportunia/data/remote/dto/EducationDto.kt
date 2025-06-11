package com.frontend.oportunia.data.remote.dto

data class EducationDto(
    val id: Long?,
    val studentId: StudentDto,
    val name: String,
    val institution: String,
    val year: Int
)