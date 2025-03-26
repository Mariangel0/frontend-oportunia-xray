package com.frontend.oportunia.domain.model

data class Curriculum(
    val id: Long,
    val studentId: Student,
    val archiveUrl: String,
    val feedback: String
)
