package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Ability

interface AbilityRepository {
    suspend fun findAllAbilities(): Result<List<Ability>>
    suspend fun findAbilityById(abilityId: Long): Result<Ability>
    suspend fun findAbilitiesByStudentId(studentId: Long): Result<List<Ability>>
}