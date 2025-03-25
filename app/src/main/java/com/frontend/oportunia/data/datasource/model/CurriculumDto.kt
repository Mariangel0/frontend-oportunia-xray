package com.frontend.oportunia.data.datasource.model

data class CurriculumDto(
    val id: Long,
    val studentId: StudentDto,
    val archiveUrl: String,
    val feedback: String
)

