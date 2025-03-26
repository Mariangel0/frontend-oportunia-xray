package com.frontend.oportunia.data.mapper

import com.frontend.oportunia.data.datasource.model.StreakDto

class StreakMapper(
    private val studentMapper: StudentMapper
) {
    fun mapToDomain(dto: StreakDto): Streak = Streak(
        id = dto.id,
        studentId = studentMapper.mapToDomain(dto.studentId),
        days = dto.days,
        lastActivity = dto.lastActivity,
        bestStreak = dto.bestStreak
    )

    fun mapToDto(domain: Streak): StreakDto = StreakDto(
        id = domain.id,
        studentId = studentMapper.mapToDto(domain.studentId),
        days = domain.days,
        lastActivity = domain.lastActivity,
        bestStreak = domain.bestStreak
    )
}