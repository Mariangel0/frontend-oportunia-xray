package com.frontend.oportunia.data.remote.dto

data class CurriculumDto(
    val id: Long,
    val studentId: StudentDto,
    val archiveUrl: String,
    val feedback: String
)

