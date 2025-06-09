package com.frontend.oportunia.data.remote.dto

data class CurriculumDto(
    val id: Long,
    val archiveUrl: String,
    val s3Key: String,
    val student: StudentDto
)

