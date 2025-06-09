package com.frontend.oportunia.data.remote.dto.quiz

data class MCQuestionDto(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)