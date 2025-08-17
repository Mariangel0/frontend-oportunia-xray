package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.EducationDataSource
import com.frontend.oportunia.data.mapper.EducationMapper
import com.frontend.oportunia.domain.error.DomainError
import com.frontend.oportunia.domain.model.Education
import com.frontend.oportunia.domain.repository.EducationRepository
import kotlinx.coroutines.flow.first
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class EducationRepositoryImpl @Inject constructor(
    private val dataSource: EducationDataSource,
    private val educationMapper: EducationMapper
) : EducationRepository {

    override suspend fun findAllEducations(): Result<List<Education>> {
        return try {
            dataSource.getAllEducations().map { educationDtos ->
                educationMapper.mapToDomainList(educationDtos)
            }
        } catch (e: UnknownHostException) {
            Result.failure(Exception("Network error: Cannot connect to server. Please check your internet connection."))
        } catch (e: Exception) {
            Result.failure(Exception("Error fetching tasks: ${e.message}"))
        }
    }

    override suspend fun createEducation(education: Education): Result<Education> {
        val educationDto = educationMapper.mapToDto(education)
        return try {
            val response = dataSource.createEducation(educationDto)
            if (response.isSuccessful && response.body() != null) {
                Result.success(educationMapper.mapToDomain(response.body()!!))
            } else {
                Result.failure(Exception("Error en la creación de la educación: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
            }
    }

    override suspend fun findEducationById(educationId: Long): Result<Education> {
        return dataSource.getEducationById(educationId).map { educationDto ->
            educationMapper.mapToDomain(educationDto)
        }
    }


}
