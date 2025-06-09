package com.frontend.oportunia.data.remote.dto

data class CompanyReviewDto(
   // val id: Long,
    val student: StudentRDto,
    val company: Long,
    val rating: Float,
    val comment: String,
    val createdAt: String
)
