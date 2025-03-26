package com.frontend.oportunia.domain.model

data class CompanyReview(
    val id: Long,
    val studentId: Student,
    val companyId: Company,
    val rating: Float,
    val comment: String,
    val createdAt: String
)