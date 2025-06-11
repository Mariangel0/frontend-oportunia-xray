package com.frontend.oportunia.domain.model

data class Student(
    val user: User? = null,
    val description: String? = null,
    val premium: Boolean? = null,
    val linkedinUrl: String? = null,
    val githubUrl: String? = null,
    val bornDate: String ? = null,
    val location: String? = null,
    var userId : Long? = null
)