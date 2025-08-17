package com.frontend.oportunia.data.remote.dto

data class StudentProgressDto(
    val id: Long,
    val studentId: StudentDto,
    val totalInterviews: Int,
    val averageScore: Float,
    val uploadedCl: Int,
    val lastActivity: String,
)