package com.frontend.oportunia.domain.model

data class IAAnalysis(
    val id: Long,
    val interviewId: Long,
    val curriculumId: Long,
    val recommendations: String,
    val score: Float,
    val date: String
)