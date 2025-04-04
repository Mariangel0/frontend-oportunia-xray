package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import kotlinx.coroutines.flow.Flow

interface CompanyReviewDataSource {
    suspend fun getCompanyReviews(): Flow<List<CompanyReviewDto>>
    suspend fun getCompanyReviewById(id: Long): CompanyReviewDto?
    suspend fun insertCompanyReview(companyReviewDto: CompanyReviewDto)
    suspend fun updateCompanyReview(companyReviewDto: CompanyReviewDto)
    suspend fun deleteCompanyReview(companyReviewDto: CompanyReviewDto)
}