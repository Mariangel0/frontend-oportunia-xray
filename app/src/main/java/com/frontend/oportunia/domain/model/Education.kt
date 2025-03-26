package com.frontend.oportunia.domain.model

data class Education(
    val id: Long,
    val studentId: Student,
    val name: String,
    val institution: String,
    val year: Int
)