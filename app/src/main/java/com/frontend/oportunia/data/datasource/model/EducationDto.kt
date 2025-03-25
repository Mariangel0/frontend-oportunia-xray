package com.frontend.oportunia.data.datasource.model

data class EducationDto(
    val id: Long,
    val studentId: StudentDto,
    val name: String,
    val institution: String,
    val year: Int
)