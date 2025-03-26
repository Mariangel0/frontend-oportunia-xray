package com.frontend.oportunia.data.repository

import android.util.Log
import com.frontend.oportunia.data.datasource.AdminDataSource
import com.frontend.oportunia.data.mapper.AdminMapper
import kotlinx.coroutines.flow.first
import java.io.IOException

class AdminRepositoryImpl(
    private val dataSource: AdminDataSource,
    private val adminMapper: AdminMapper
) : AdminRepository {

    companion object {
        private const val TAG = "AdminRepository"
    }

    override suspend fun findAllAdmins(): Result<List<Admin>> = runCatching {
        dataSource.getAdmins().first().map { adminDto ->
            adminMapper.mapToDomain(adminDto)
        }
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch admins", throwable)

        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch admins")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping admins")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }

    override suspend fun findAdminById(adminId: Long): Result<Admin> = runCatching {
        val adminDto = dataSource.getAdminById(adminId) ?: throw DomainError.AdminError("Admin not found")
        adminMapper.mapToDomain(adminDto)
    }.recoverCatching { throwable ->
        Log.e(TAG, "Failed to fetch admin with ID: $adminId", throwable)
        when (throwable) {
            is IOException -> throw DomainError.NetworkError("Failed to fetch admin")
            is IllegalArgumentException -> throw DomainError.MappingError("Error mapping admin")
            is DomainError -> throw throwable
            else -> throw DomainError.UnknownError
        }
    }
}
