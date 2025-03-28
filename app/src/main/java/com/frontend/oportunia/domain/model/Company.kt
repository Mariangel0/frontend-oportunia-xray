package com.frontend.oportunia.domain.model

data class Company(
    val id: Long,
    val name: String,
    val description: String,
    val type: String,
    val ubication: String,
    val employees: Int,
    val websiteUrl: String,
    val rating: Float
)
