package com.frontend.oportunia.data.datasource.model

data class IAAnalysisDto(
    val id: Long,
    val interviewId: Long,
    val curriculumId: Long,
    val recommendations: String,
    val score: Float,
    val date: String
)