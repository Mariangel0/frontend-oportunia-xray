package com.frontend.oportunia.data.remote.dto

import java.util.Date

open class UserDto(
    val id: Long?,
    val createDate: Date?,
    val email: String?,
    val enabled: Boolean?,
    val firstName: String,
    val lastName: String,
    val password: String?,
    val tokenExpired: Boolean?,
    val roles: List<RoleDto>?
)

open class UserRDto(
    val firstName: String,
    val lastName: String,
)

