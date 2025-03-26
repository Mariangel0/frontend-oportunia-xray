package com.frontend.oportunia.domain.model

data class Student(
    val id: User,
    val description: String,
    val premium: Boolean,
    val linkedinUrl: String,
    val githubUrl: String
)