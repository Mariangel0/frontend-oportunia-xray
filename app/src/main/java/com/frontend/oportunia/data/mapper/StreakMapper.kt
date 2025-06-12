package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.StreakDto
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Streak

class StreakMapper(
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: StreakDto): Streak = Streak(
        id = dto.id,
        student = studentMapper.mapToDomain(dto.student),
        days = dto.days,
        lastActivity = dto.lastActivity,
        bestStreak = dto.bestStreak
    )

    fun mapToDto(domain: Streak): StreakDto = StreakDto(
        id = domain.id,
        student = studentMapper.mapToDto(domain.student),
        days = domain.days,
        lastActivity = domain.lastActivity,
        bestStreak = domain.bestStreak
    )

    fun mapToDomainList(StreakDto: List<StreakDto>): List<Streak> {
        return StreakDto.map { mapToDomain(it) }
    }
}