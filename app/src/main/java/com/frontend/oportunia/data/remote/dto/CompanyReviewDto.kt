package com.frontend.oportunia.data.remote.dto

data class CompanyReviewDto(
    val id: Long,
    val studentId: StudentDto,
    val companyId: CompanyDto,
    val rating: Float,
    val comment: String,
    val createdAt: String
)
