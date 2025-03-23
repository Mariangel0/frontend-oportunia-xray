package com.frontend.oportunia.data.datasource.model

data class StudentProgressDto(
    val id: Long,
    val studentId: Long,
    val totalInterviews: Int,
    val averageScore: Float,
    val uploadedCl: Int,
    val lastActivity: String
)