package com.frontend.oportunia.data.mapper
import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import com.frontend.oportunia.domain.model.CompanyReview
import javax.inject.Inject

class CompanyReviewMapper  @Inject constructor(
    private val studentMapper: StudentMapper,
    private val companyMapper: CompanyMapper
) {
    fun mapToDomain(dto: CompanyReviewDto): CompanyReview {
        return CompanyReview(
         //   id = dto.id,
            student = studentMapper.mapToDomainRDto(dto.student),
            company =  dto.company,
            rating = dto.rating,
            comment = dto.comment,
            createdAt = dto.createdAt
        )
    }

    fun mapToDomainList(companyReviewDto: List<CompanyReviewDto>): List<CompanyReview> {
        return companyReviewDto.map { mapToDomain(it) }
    }

    fun mapToDto(domain: CompanyReview): CompanyReviewDto {
        return CompanyReviewDto(
            //id = domain.id,
            student = studentMapper.mapToDtoRDto(domain.student),
            company = domain.company,
            rating = domain.rating,
            comment = domain.comment,
            createdAt = domain.createdAt
        )
    }

    fun mapToDtoList(companyReviews: List<CompanyReview>): List<CompanyReviewDto> {
        return companyReviews.map { mapToDto(it) }
    }



}