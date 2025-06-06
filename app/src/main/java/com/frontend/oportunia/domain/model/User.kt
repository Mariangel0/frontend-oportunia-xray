package com.frontend.oportunia.domain.model

import java.util.Date

data class User(
    val id: Long,
    val createDate: Date,
    val email: String,
    val enabled: Boolean,
    val firstName: String,
    val lastName: String,
    val password: String,
    val tokenExpired: Boolean
)
