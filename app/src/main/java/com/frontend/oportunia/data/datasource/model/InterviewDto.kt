package com.frontend.oportunia.data.datasource.model

data class InterviewDto(
    val id: Long,
    val studentId: StudentDto,
    val date: String,
    val result: String
)