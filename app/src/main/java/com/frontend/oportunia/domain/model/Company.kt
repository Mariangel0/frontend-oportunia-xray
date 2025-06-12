package com.frontend.oportunia.domain.model

data class Company(
    val id: Long? = null,
    val name: String,
    val description: String,
    val rating: Float,
    val type: String? = null,
    val location: String? = null,
    val employees: Int? = null,
    val websiteUrl: String? = null,
    val vision: String? = null,
    val mission: String? = null
)

