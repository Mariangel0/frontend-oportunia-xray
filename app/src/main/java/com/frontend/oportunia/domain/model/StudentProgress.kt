package com.frontend.oportunia.domain.model

data class StudentProgress(
    val id: Long,
    val studentId: Student,
    val totalInterviews: Int,
    val averageScore: Float,
    val uploadedCl: Int,
    val lastActivity: String
)