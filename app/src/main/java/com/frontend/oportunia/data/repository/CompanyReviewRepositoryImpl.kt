package com.frontend.oportunia.data.repository

import com.frontend.oportunia.data.datasource.CompanyReviewDataSource
import com.frontend.oportunia.data.mapper.CompanyReviewMapper
import com.frontend.oportunia.domain.model.CompanyReview
import com.frontend.oportunia.domain.repository.CompanyReviewRepository
import java.net.UnknownHostException
import javax.inject.Inject

class CompanyReviewRepositoryImpl @Inject constructor(
    private val dataSource: CompanyReviewDataSource,
    private val companyReviewMapper: CompanyReviewMapper
) : CompanyReviewRepository {

    override suspend fun findAllCompanyReviews(): Result<List<CompanyReview>> {
        return try {
            dataSource.getAllReviews().map { reviewDtos ->
                companyReviewMapper.mapToDomainList(reviewDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findCompanyReviewById(companyReviewId: Long): Result<CompanyReview> =
        dataSource.getReviewById(companyReviewId).map { reviewDto ->
            companyReviewMapper.mapToDomain(reviewDto)
        }

    override suspend fun findCompanyReviewsByCompanyId(companyId: Long): Result<List<CompanyReview>> {
        val response = dataSource.getReviewsByCompanyId(companyId)
        println("RESPONSE TYPE = ${response::class.qualifiedName}")

        return try {
            dataSource.getReviewsByCompanyId(companyId).map { reviewDtos ->
                companyReviewMapper.mapToDomainList(reviewDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }


}
