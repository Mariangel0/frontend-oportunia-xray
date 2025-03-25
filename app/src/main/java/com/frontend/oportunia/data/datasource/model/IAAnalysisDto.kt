package com.frontend.oportunia.data.datasource.model

data class IAAnalysisDto(
    val id: Long,
    val interviewId: InterviewDto,
    val curriculumId: CurriculumDto,
    val recommendations: String,
    val score: Float,
    val date: String
)