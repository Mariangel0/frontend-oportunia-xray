package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Company

interface CompanyRepository {
    suspend fun findAllCompanies(): Result<List<Company>>
    suspend fun findCompanyById(companyId: Long): Result<Company>
}