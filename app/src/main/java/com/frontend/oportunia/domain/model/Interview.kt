package com.frontend.oportunia.domain.model

data class Interview(
    val id: Long,
    val date: String,
    val result: String,
    var jobPosition: String,
    var type: String,
    val student: Student,
)