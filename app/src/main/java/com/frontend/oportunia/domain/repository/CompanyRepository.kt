package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Company

interface CompanyRepository {
    suspend fun findAllCompanies(): Result<List<Company>>
    suspend fun findCompanyById(companyId: Long): Result<Company>
    suspend fun findCompanyByName(companyName: String): Result<List<Company>>
    suspend fun createCompany(company: Company): Result<Company>
}