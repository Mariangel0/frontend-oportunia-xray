package com.frontend.oportunia.data.mapper
import com.frontend.oportunia.data.datasource.model.CompanyReviewDto

class CompanyReviewMapper {
    fun mapToDomain(dto: CompanyReviewDto): CompanyReview = CompanyReview(
        id = dto.id,
        studentId = dto.studentId,
        companyId = dto.companyId,
        rating = dto.rating,
        comment = dto.comment,
        createdAt = dto.createdAt
    )

    fun mapToDto(domain: CompanyReview): CompanyReviewDto = CompanyReviewDto(
        id = domain.id,
        studentId = domain.studentId,
        companyId = domain.companyId,
        rating = domain.rating,
        comment = domain.comment,
        createdAt = domain.createdAt
    )
}