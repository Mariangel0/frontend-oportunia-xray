package com.frontend.oportunia.domain.model

data class Curriculum(
    val id: Long,
    val studentId: Long,
    val archiveUrl: String,
    val feedback: String
)
