package com.frontend.oportunia.data.datasource

import com.frontend.oportunia.data.datasource.model.CompanyDto
import com.frontend.oportunia.data.mapper.CompanyMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CompanyDataSourceImpl(
    private val companyMapper: CompanyMapper
) : CompanyDataSource {

    override suspend fun getCompanies(): Flow<List<CompanyDto>> = flow {
        val companies = CompanyProvider.findAllCompanies()  // Simula la obtención de compañías
        emit(companies.map { companyMapper.mapToDto(it) })
    }

    override suspend fun getCompanyById(id: Long): CompanyDto? = CompanyProvider.findCompanyById(id)?.let {
        companyMapper.mapToDto(it)
    }

    override suspend fun insertCompany(companyDto: CompanyDto) {
    }

    override suspend fun updateCompany(companyDto: CompanyDto) {
    }

    override suspend fun deleteCompany(companyDto: CompanyDto) {
    }

    override suspend fun getCompaniesByName(companyName: String): Flow<List<CompanyDto>> = flow {
        val companies = CompanyProvider.findAllCompanies()
        val filteredCompanies = companies.filter { it.name.contains(companyName, ignoreCase = true) }
        emit(filteredCompanies.map { companyMapper.mapToDto(it) })
    }
}
