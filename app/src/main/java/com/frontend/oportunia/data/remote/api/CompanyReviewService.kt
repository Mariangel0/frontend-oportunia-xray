package com.frontend.oportunia.data.remote.api


import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CompanyReviewService {

    @GET("reviews")
    suspend fun getAllReviews(): Response<List<CompanyReviewDto>>

    @GET("reviews/{id}")
    suspend fun getReviewById(@Path("id") id: Long): Response<CompanyReviewDto>

    @GET("reviews")
    suspend fun getReviewsByCompanyId(@Query("companyId.id") companyId: Long): Response<List<CompanyReviewDto>>


    @POST("reviews")
    suspend fun createReview(@Body review: CompanyReviewDto): Response<CompanyReviewDto>

    @PUT("reviews/{id}")
    suspend fun updateReview(
        @Path("id") id: Long,
        @Body review: CompanyReviewDto
    ): Response<CompanyReviewDto>

    @DELETE("reviews/{id}")
    suspend fun deleteReview(@Path("id") id: Long): Response<Unit>
}