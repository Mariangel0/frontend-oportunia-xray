package com.frontend.oportunia.data.repository

import com.frontend.oportunia.data.datasource.CompanyDataSource
import com.frontend.oportunia.data.mapper.CompanyMapper
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.repository.CompanyRepository
import java.net.UnknownHostException
import javax.inject.Inject

class CompanyRepositoryImpl  @Inject constructor (
    private val dataSource: CompanyDataSource,
    private val companyMapper: CompanyMapper
) : CompanyRepository {


    override suspend fun findAllCompanies(): Result<List<Company>> {
        return try {
            dataSource.getAllCompanies().map { companyDtos ->
                companyMapper.mapToDomainList(companyDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findCompanyById(companyId: Long): Result<Company> =
        dataSource.getCompanyById(companyId).map { companyDto ->
            companyMapper.mapToDomain(companyDto)
        }


    override suspend fun findCompanyByName(companyName: String): Result<List<Company>> {
        return try {
            dataSource.getCompanyByName(companyName).map { companyDtos ->
                companyMapper.mapToDomainList(companyDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }
}


//    override suspend fun findCompanyById(companyId: Long): Result<Company> = runCatching {
//        val companyDto = dataSource.getCompanyById(companyId) ?: throw DomainError.CompanyError("Company not found")
//        companyMapper.mapToDomain(companyDto)
//    }.recoverCatching { throwable ->
//        Log.e(TAG, "Failed to fetch company with ID: $companyId", throwable)
//        when (throwable) {
//            is IOException -> throw DomainError.NetworkError("Failed to fetch company")
//            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping company")
//            is DomainError -> throw throwable
//            else -> throw DomainError.UnknownError("An unknown error occurred")
//        }
//    }
//
//    override suspend fun findCompanyByName(companyName: String): Result<List<Company>> = runCatching {
//        dataSource.getCompaniesByName(companyName).first().map { companyDto ->
//            companyMapper.mapToDomain(companyDto)
//        }
//    }.recoverCatching { throwable ->
//        Log.e(TAG, "Failed to search for companies with name: $companyName", throwable)
//
//        when (throwable) {
//            is IOException -> throw DomainError.NetworkError("Failed to fetch companies by name")
//            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping companies")
//            is DomainError -> throw throwable
//            else -> throw DomainError.UnknownError("An unknown error occurred")
//        }
//    }


