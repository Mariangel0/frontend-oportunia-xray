package com.frontend.oportunia.domain.model

data class Ability(
    val id: Long,
    val studentId: Student,
    val name: String
)