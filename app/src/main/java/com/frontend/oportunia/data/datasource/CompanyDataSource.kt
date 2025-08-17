package com.frontend.oportunia.data.datasource

import android.util.Log
import com.frontend.oportunia.data.remote.api.CompanyService
import com.frontend.oportunia.data.remote.dto.CompanyDto
import javax.inject.Inject
import retrofit2.Response

class CompanyDataSource @Inject constructor(
    private val companyService: CompanyService
) {

    suspend fun getAllCompanies(): Result<List<CompanyDto>> = safeApiCall {
        companyService.getAllCompanies()
    }


    suspend fun getCompanyById(id: Long): Result<CompanyDto>  = safeApiCall {
         companyService.getCompanyById(id)
    }

    suspend fun createCompany(companyDto: CompanyDto): Response<CompanyDto> {
        return companyService.createCompany(companyDto)
    }


    suspend fun updateCompany(id: Long, companyDto: CompanyDto): Response<CompanyDto> {
        return companyService.updateCompany(id, companyDto)
    }

    suspend fun getCompanyByName(name: String): Result<List<CompanyDto>> = safeApiCall {
        companyService.getCompanyByName(name)
    }


    suspend fun deleteCompany(id: Long): Response<Unit> {
        return companyService.deleteCompany(id)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        Log.d("API", "Response: ${response.code()} - ${response.body()}")
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("API", "Error: $errorBody")
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Log.e("API", "Exception: ${e.message}", e)
        Result.failure(e)
    }

}