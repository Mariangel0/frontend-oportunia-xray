package com.frontend.oportunia.domain.model

data class AnalyzedCVResponse(
    val recomendaciones: List<String>,
    val comentarios: String,
    val score: Float
)