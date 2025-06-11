package com.frontend.oportunia.domain.model

data class Experience(
    val id: Long? = null,
    val timeline: String,
    val studentId: Student,
    val company: String,
    val role: String,
)