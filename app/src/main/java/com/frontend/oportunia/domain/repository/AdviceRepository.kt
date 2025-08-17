package com.frontend.oportunia.domain.repository

import com.frontend.oportunia.domain.model.Advice

interface AdviceRepository {
    suspend fun findAllAdvices(): Result<List<Advice>>
    suspend fun findAdviceById(adviceId: Long): Result<Advice>
}