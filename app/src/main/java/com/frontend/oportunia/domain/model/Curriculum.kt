package com.frontend.oportunia.domain.model

data class Curriculum(
    val id: Long,
    val archiveUrl: String,
    val s3Key: String,
    val student: Student,
)
