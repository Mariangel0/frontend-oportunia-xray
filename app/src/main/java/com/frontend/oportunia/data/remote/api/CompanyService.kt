package com.frontend.oportunia.data.remote.api

import com.frontend.oportunia.data.remote.dto.CompanyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyService {

    @GET("companies")
    suspend fun getAllCompanies(): Response<List<CompanyDto>>

    @GET("companies/{id}")
    suspend fun getCompanyById(@Path("id") id: Long): Response<CompanyDto>

    @POST("companies")
    suspend fun createCompany(@Body company: CompanyDto): Response<CompanyDto>

    @PUT("companies/{id}")
    suspend fun updateCompany(
        @Path("id") id: Long,
        @Body company: CompanyDto
    ): Response<CompanyDto>

    @DELETE("companies/{id}")
    suspend fun deleteCompany(@Path("id") id: Long): Response<Unit>

    @GET("companies")
    suspend fun getCompanyByName(@Query("name") name: String): Response<List<CompanyDto>>
}