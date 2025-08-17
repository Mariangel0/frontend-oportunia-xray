package com.frontend.oportunia.domain.model

data class Education(
    val id: Long? = null,
    val student: Student,
    val name: String,
    val institution: String,
    val year: Int
)