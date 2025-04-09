package com.frontend.oportunia.data.remote.dto

data class CompanyReviewDto(
    val id: Long,
    val studentId: Long,
    val companyId: Long,
    val rating: Float,
    val comment: String,
    val createdAt: String
)
