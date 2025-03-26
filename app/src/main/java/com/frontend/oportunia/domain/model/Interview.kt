package com.frontend.oportunia.domain.model

data class Interview(
    val id: Long,
    val studentId: Student,
    val date: String,
    val result: String
)