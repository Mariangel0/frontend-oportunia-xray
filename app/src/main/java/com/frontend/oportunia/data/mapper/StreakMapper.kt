package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import com.frontend.oportunia.data.remote.dto.StreakDto
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.model.Streak
import javax.inject.Inject

class StreakMapper @Inject constructor (
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: StreakDto): Streak {
        return Streak(
            id = dto.id,
            days = dto.days,
            lastActivity = dto.lastActivity,
            bestStreak = dto.bestStreak,
            student = studentMapper.mapToDomain(dto.student),
        )
    }

    fun mapToDto(domain: Streak): StreakDto = StreakDto(
        id = domain.id,
        days = domain.days,
        lastActivity = domain.lastActivity,
        bestStreak = domain.bestStreak,
        student = studentMapper.mapToDto(domain.student),
    )

    fun mapToDomainList(StreakDto: List<StreakDto>): List<Streak> {
        return StreakDto.map { mapToDomain(it) }
    }
}