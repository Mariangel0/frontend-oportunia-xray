package com.frontend.oportunia.domain.model

data class Experience(
    val id: Long,
    val studentId: Long,
    val company: Long,
    val role: String,
    val year: Int
)