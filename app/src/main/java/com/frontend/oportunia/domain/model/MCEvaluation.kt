package com.frontend.oportunia.domain.model

data class MCEvaluation(
    val question: String,
    val selectedOption: String,
    val correctAnswer: String,
    val isCorrect: Boolean
)
