package com.frontend.oportunia.data.datasource.model

open class UserDto(
    val id: Long,
    val createDate: String,
    val email: String,
    val enabled: Boolean,
    val firstName: String,
    val lastName: String,
    val password: String,
    val tokenExpired: Boolean
)
