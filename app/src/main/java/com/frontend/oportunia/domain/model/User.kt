package com.frontend.oportunia.domain.model

import java.util.Date

data class User(
    val id: Long? = null,
    val createDate: Date? = null,
    val email: String? = null,
    val enabled: Boolean? = null,
    val firstName: String,
    val lastName: String,
    val password: String? = null,
    val tokenExpired: Boolean? = null,
    val roles: List<Role>? = null
)
