package com.frontend.oportunia.domain.model

data class CompanyReview(
    //val id: Long,
    val student: Student,
    val company: Long,
    val rating: Float,
    val comment: String,
    val createdAt: String
)