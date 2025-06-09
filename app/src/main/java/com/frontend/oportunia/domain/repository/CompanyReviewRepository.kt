package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.CompanyReview

interface CompanyReviewRepository {
    suspend fun createCompanyReview(companyReview: CompanyReview): Result<CompanyReview>
    suspend fun findAllCompanyReviews(): Result<List<CompanyReview>>
    suspend fun findCompanyReviewById(companyReviewId: Long): Result<CompanyReview>
    suspend fun findCompanyReviewsByCompanyId(companyId: Long): Result<List<CompanyReview>>
}