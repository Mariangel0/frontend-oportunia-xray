package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.domain.model.Company
import javax.inject.Inject

class CompanyMapper @Inject constructor() {
    fun mapToDomain(dto: CompanyDto): Company{
        return Company(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            type = dto.type,
            ubication = dto.ubication,
            employees = dto.employees,
            websiteUrl = dto.websiteUrl,
            rating = dto.rating
        )
    }

    fun mapToDomainList(companyDto: List<CompanyDto>): List<Company> {
        return companyDto.map { mapToDomain(it) }
    }


    fun mapToDto(domain: Company): CompanyDto {
        return CompanyDto(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            type = domain.type,
            ubication = domain.ubication,
            employees = domain.employees,
            websiteUrl = domain.websiteUrl,
            rating = domain.rating

        )
    }

    fun mapToDtoList(companies: List<Company>): List<CompanyDto> {
        return companies.map { mapToDto(it) }
    }
}