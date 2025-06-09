package com.frontend.oportunia.data.remote.dto.quiz

data class MCEvaluationDto(
    val question: String,
    val selectedOption: String,
    val correctAnswer: String,
    val isCorrect: Boolean
)
