package com.frontend.oportunia.domain.model

data class MCQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String 
)