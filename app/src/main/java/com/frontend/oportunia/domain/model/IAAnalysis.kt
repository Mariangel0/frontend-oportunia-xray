package com.frontend.oportunia.domain.model

data class IAAnalysis(
    val id: Long,
    val interviewId: Interview,
    val curriculumId: Curriculum,
    val recommendations: String,
    val score: Float,
    val date: String
)