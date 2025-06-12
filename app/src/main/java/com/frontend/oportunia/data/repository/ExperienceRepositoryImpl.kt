package com.frontend.oportunia.data.repository

import com.frontend.oportunia.data.datasource.ExperienceDataSource
import com.frontend.oportunia.data.mapper.ExperienceMapper
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.domain.repository.ExperienceRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ExperienceRepositoryImpl @Inject constructor (
    private val dataSource: ExperienceDataSource,
    private val experienceMapper: ExperienceMapper
) : ExperienceRepository {

    override suspend fun findAllExperiences(): Result<List<Experience>> {
        return try {
            dataSource.getAllExperiences().map { experienceDtos ->
                experienceMapper.mapToDomainList(experienceDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun findExperienceById(experienceId: Long): Result<Experience> =
        dataSource.getExperienceById(experienceId).map { experienceDto ->
            experienceMapper.mapToDomain(experienceDto)
        }

    override suspend fun findExperiencesByStudentId(studentId: Long): Result<List<Experience>> {
        return try {
            dataSource.getExperiencesByStudentId(studentId).map { experienceDtos ->
                experienceMapper.mapToDomainList(experienceDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }
    override suspend fun createExperience(experience: Experience): Result<Experience> {
        val reviewDto =  experienceMapper.mapToDto(experience)
        return try {
            val response = dataSource.createExperience(reviewDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(experienceMapper.mapToDomain(response.body()!!))
            } else {
                Result.failure(Exception("Error en la creación de la experiencia: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
