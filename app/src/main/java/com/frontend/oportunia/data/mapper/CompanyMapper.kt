package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.CompanyDto
import com.frontend.oportunia.domain.model.Company

class CompanyMapper {
    fun mapToDomain(dto: CompanyDto): Company = Company(
        id = dto.id,
        name = dto.name,
        description = dto.description,
        type = dto.type,
        ubication = dto.ubication,
        employees = dto.employees,
        websiteUrl = dto.websiteUrl,
        rating = dto.rating
    )

    fun mapToDto(domain: Company): CompanyDto = CompanyDto(
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