package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.CompanyReviewDataSource
import com.frontend.oportunia.data.mapper.CompanyReviewMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class CompanyReviewRepositoryImpl(
    private val dataSource: CompanyReviewDataSource,
    private val companyReviewMapper: CompanyReviewMapper
) : CompanyReviewRepository {

    companion object {
        private const val TAG = "CompanyReviewRepository"
    }

    override suspend fun findAllCompanyReviews(): Result<List<CompanyReview>> = runCatching {
        dataSource.getCompanyReviews().first().map { companyReviewDto ->
            companyReviewMapper.mapToDomain(companyReviewDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch company reviews", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch company reviews")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping company reviews")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findCompanyReviewById(companyReviewId: Long): Result<CompanyReview> = runCatching {
        val companyReviewDto = dataSource.getCompanyReviewById(companyReviewId) ?: throw DomainError.CompanyReviewError("Company review not found")
        companyReviewMapper.mapToDomain(companyReviewDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch company review with ID: $companyReviewId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch company review")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping company review")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
