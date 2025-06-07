package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.CompanyDto
import com.frontend.oportunia.data.remote.dto.CompanySDto
import com.frontend.oportunia.domain.model.Company
import javax.inject.Inject

class CompanyMapper @Inject constructor() {
    fun mapToDomain(dto: CompanyDto): Company{
        return Company(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            type = dto.type,
            location = dto.location,
            employees = dto.employees,
            websiteUrl = dto.websiteUrl,
            rating = dto.rating,
            vision = dto.vision,
            mission = dto.mission
        )
    }

    fun mapToDomainSDto(dto: CompanySDto): Company {
        return Company(
            id = dto.id,
            name = dto.name,
            description = dto.description,
            rating = dto.rating,
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
            location = domain.location,
            employees = domain.employees,
            websiteUrl = domain.websiteUrl,
            rating = domain.rating,
            vision = domain.vision,
            mission = domain.mission
        )
    }

    fun mapToDtoList(companies: List<Company>): List<CompanyDto> {
        return companies.map { mapToDto(it) }
    }
}