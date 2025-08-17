package com.frontend.oportunia.data.remote.dto


data class AnalyzedCVResponseDto(
    val recomendaciones: List<String>,
    val comentarios: String,
    val score : Float
)