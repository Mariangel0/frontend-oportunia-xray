package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.CompanyReviewDto
import com.frontend.oportunia.data.mapper.CompanyReviewMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CompanyReviewDataSourceImpl(
    private val companyReviewMapper: CompanyReviewMapper
) : CompanyReviewDataSource {

    override suspend fun getCompanyReviews(): Flow<List<CompanyReviewDto>> = flow {
        val reviews = CompanyReviewProvider.findAllCompanyReviews()  // Simula la obtención de reseñas
        emit(reviews.map { companyReviewMapper.mapToDto(it) })
    }

    override suspend fun getCompanyReviewById(id: Long): CompanyReviewDto? = CompanyReviewProvider.findCompanyReviewById(id)?.let {
        companyReviewMapper.mapToDto(it)
    }

    override suspend fun insertCompanyReview(companyReviewDto: CompanyReviewDto) {
    }

    override suspend fun updateCompanyReview(companyReviewDto: CompanyReviewDto) {
    }

    override suspend fun deleteCompanyReview(companyReviewDto: CompanyReviewDto) {
    }
}
