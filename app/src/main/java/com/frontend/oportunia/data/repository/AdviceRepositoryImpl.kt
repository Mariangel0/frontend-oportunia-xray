package com.frontend.oportunia.data.repository

import com.frontend.oportunia.data.datasource.AdviceDataSource
import com.frontend.oportunia.data.mapper.AdviceMapper
import com.frontend.oportunia.domain.model.Advice
import com.frontend.oportunia.domain.model.Company
import com.frontend.oportunia.domain.repository.AdviceRepository
import java.net.UnknownHostException
import javax.inject.Inject

class AdviceRepositoryImpl @Inject constructor (
    private val dataSource: AdviceDataSource,
    private val adviceMapper: AdviceMapper
) : AdviceRepository {

    override suspend fun findAllAdvices(): Result<List<Advice>> {
        return try {
            dataSource.getAllAdvices().map { adviceDtos ->
                adviceMapper.mapToDomainList(adviceDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findAdviceById(adviceId: Long): Result<Advice> =
        dataSource.getAdvicesById(adviceId).map { adviceDto ->
            adviceMapper.mapToDomain(adviceDto)
        }
}