package com.frontend.oportunia.domain.model

data class Experience(
    val id: Long? = null,
    val timeline: String,
    val role: String,
    val student: Student,
    val company: String,
)