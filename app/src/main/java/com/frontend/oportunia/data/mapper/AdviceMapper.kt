package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.AdviceDto
import com.frontend.oportunia.domain.model.Advice
import javax.inject.Inject

class AdviceMapper @Inject constructor() {

    fun mapToDomain(dto: AdviceDto): Advice {
        return Advice(
            id = dto.id,
            question = dto.question,
            answer = dto.answer
        )
    }

    fun mapToDomainList(adviceDto: List<AdviceDto>): List<Advice> {
        return adviceDto.map { mapToDomain(it) }
    }

    fun mapToDto(domain: Advice): AdviceDto {
        return AdviceDto(
            id = domain.id,
            question = domain.question,
            answer = domain.answer
        )
    }

    fun mapToDtoList(advices: List<Advice>): List<AdviceDto> {
        return advices.map { mapToDto(it) }
    }
}