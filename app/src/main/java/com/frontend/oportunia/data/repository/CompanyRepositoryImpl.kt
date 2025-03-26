package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.CompanyDataSource
import com.frontend.oportunia.data.mapper.CompanyMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class CompanyRepositoryImpl(
    private val dataSource: CompanyDataSource,
    private val companyMapper: CompanyMapper
) : CompanyRepository {

    companion object {
        private const val TAG = "CompanyRepository"
    }

    override suspend fun findAllCompanies(): Result<List<Company>> = runCatching {
        dataSource.getCompanies().first().map { companyDto ->
            companyMapper.mapToDomain(companyDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch companies", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch companies")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping companies")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findCompanyById(companyId: Long): Result<Company> = runCatching {
        val companyDto = dataSource.getCompanyById(companyId) ?: throw DomainError.CompanyError("Company not found")
        companyMapper.mapToDomain(companyDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch company with ID: $companyId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch company")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping company")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
