package com.frontend.oportunia.domain.model

data class CompanyReview(
    val id: Long,
    val studentId: Long,
    val companyId: Long,
    val rating: Float,
    val comment: String,
    val createdAt: String
)