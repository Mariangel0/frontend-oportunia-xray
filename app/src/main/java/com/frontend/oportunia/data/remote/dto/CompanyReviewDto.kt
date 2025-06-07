package com.frontend.oportunia.data.remote.dto

data class CompanyReviewDto(
    val id: Long,
    val studentId: StudentRDto,
    val companyId: CompanySDto,
    val rating: Float,
    val comment: String,
    val createdAt: String
)
