package com.frontend.oportunia.data.remote.dto

data class InterviewDto(
    val id: Long,
    val studentId: StudentDto,
    val date: String,
    val result: String
)

