package com.frontend.oportunia.data.repository

import com.frontend.oportunia.data.datasource.AbilityDataSource
import com.frontend.oportunia.data.mapper.AbilityMapper
import com.frontend.oportunia.domain.model.Ability
import com.frontend.oportunia.domain.repository.AbilityRepository
import java.net.UnknownHostException
import javax.inject.Inject

class AbilityRepositoryImpl @Inject constructor(
    private val dataSource: AbilityDataSource,
    private val abilityMapper: AbilityMapper
) : AbilityRepository {

    override suspend fun findAllAbilities(): Result<List<Ability>> {
        return try {
            dataSource.getAllAbilities().map { abilityDtos ->
                abilityMapper.mapToDomainList(abilityDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findAbilityById(abilityId: Long): Result<Ability> =
        dataSource.getAbilityById(abilityId).map { reviewDto ->
            abilityMapper.mapToDomain(reviewDto)
        }

    override suspend fun findAbilitiesByStudentId(studentId: Long): Result<List<Ability>> {
        return try {
            dataSource.getAbilitiesByStudentId(studentId).map { abilityDtos ->
                abilityMapper.mapToDomainList(abilityDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

}