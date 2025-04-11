package com.frontend.oportunia.data.remote.dto

data class CompanyDto(
    val id: Long,
    val name: String,
    val description: String,
    val type: String,
    val ubication: String,
    val employees: Int,
    val websiteUrl: String,
    val rating: Float

)