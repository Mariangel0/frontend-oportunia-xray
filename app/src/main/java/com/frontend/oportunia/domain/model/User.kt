package com.frontend.oportunia.domain.model

data class User(
    val id: Long,
    val createDate: String,
    val email: String,
    val enabled: Boolean,
    val firstName: String,
    val lastName: String,
    val password: String,
    val tokenExpired: Boolean
)
