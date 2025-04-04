package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.remote.dto.CompanyDto
import kotlinx.coroutines.flow.Flow

interface CompanyDataSource {
    suspend fun getCompanies(): Flow<List<CompanyDto>>
    suspend fun getCompanyById(id: Long): CompanyDto?
    suspend fun insertCompany(companyDto: CompanyDto)
    suspend fun updateCompany(companyDto: CompanyDto)
    suspend fun deleteCompany(companyDto: CompanyDto)
    suspend fun getCompaniesByName(companyName: String): Flow<List<CompanyDto>>
}