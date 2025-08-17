package com.frontend.oportunia.data.remote.dto

data class CompanyDto(
    val id: Long? = null,
    val name: String,
    val description: String,
    val rating: Float,
    val type: String?,
    val location: String?,
    val employees: Int?,
    val websiteUrl: String?,
    val vision: String?,
    val mission: String?
)

