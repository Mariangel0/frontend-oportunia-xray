package com.frontend.oportunia.data.datasource.model

data class CompanyDto(
    val id: Long,
    val name: String,
    val description: String,
    val type: String,
    val ubication: String,
    val employees: Int,
    val websiteUrl: String
)