package com.frontend.oportunia.data.remote.dto

import com.frontend.oportunia.domain.model.Interview

data class IAAnalysisDto(
    val id: Long,
    val recommendation: String,
    val comment: String,
    val score: Float,
    val date: String,
    val interview: InterviewDto,
)