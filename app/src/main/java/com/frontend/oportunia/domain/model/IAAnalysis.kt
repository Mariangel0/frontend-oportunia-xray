package com.frontend.oportunia.domain.model

data class IAAnalysis(
    val id: Long,
    val recommendation: String,
    val comment: String,
    val score: Float,
    val date: String,
    val interview: Interview,
)

