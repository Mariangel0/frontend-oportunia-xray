package com.frontend.oportunia.domain.model

data class Experience(
    val id: Long,
    val studentId: Student,
    val company: Company,
    val role: String,
    val year: Int
)