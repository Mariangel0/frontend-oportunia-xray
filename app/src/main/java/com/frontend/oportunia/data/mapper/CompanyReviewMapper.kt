package com.frontend.oportunia.data.mapper
import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import com.frontend.oportunia.domain.model.CompanyReview

class CompanyReviewMapper(
    private val studentMapper: StudentMapper,
    private val companyMapper: CompanyMapper
) {
    fun mapToDomain(dto: CompanyReviewDto): CompanyReview = CompanyReview(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        companyId = companyMapper.mapToDomain(dto.companyId),
        rating = dto.rating,
        comment = dto.comment,
        createdAt = dto.createdAt
    )

    fun mapToDto(domain: CompanyReview): CompanyReviewDto = CompanyReviewDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        companyId = companyMapper.mapToDto(domain.companyId),
        rating = domain.rating,
        comment = domain.comment,
        createdAt = domain.createdAt
    )
}