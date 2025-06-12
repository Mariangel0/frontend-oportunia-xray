package com.frontend.oportunia.domain.model

data class Student(
    val id: Long? = null,
    val description: String? = null,
    val premium: Boolean? = null,
    val linkedinUrl: String? = null,
    val githubUrl: String? = null,
    val bornDate: String ? = null,
    val location: String? = null,
    val user: User? = null,
)