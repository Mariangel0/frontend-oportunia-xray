package com.frontend.oportunia.data.di




import com.frontend.oportunia.data.remote.api.AbilityService
import com.frontend.oportunia.data.remote.api.AdviceService
import com.frontend.oportunia.data.remote.api.AuthService
import com.frontend.oportunia.data.remote.api.CompanyReviewService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.frontend.oportunia.data.remote.api.CompanyService
import com.frontend.oportunia.data.remote.api.CurriculumService
import com.frontend.oportunia.data.remote.api.ExperienceService
import com.frontend.oportunia.data.remote.api.InterviewService
import com.frontend.oportunia.data.remote.api.QuizService
import com.frontend.oportunia.data.remote.api.StudentService
import com.frontend.oportunia.data.remote.api.UserService
import com.frontend.oportunia.data.remote.dto.AbilityDto
import com.frontend.oportunia.data.remote.dto.CompanyReviewDto
import com.frontend.oportunia.data.remote.dto.ExperienceDto
import com.frontend.oportunia.data.remote.dto.StudentDto
import com.frontend.oportunia.data.remote.dto.UserDto
import com.frontend.oportunia.data.remote.interceptor.AuthInterceptor
import com.frontend.oportunia.data.remote.interceptor.ResponseInterceptor
import com.frontend.oportunia.data.remote.serializer.AbilityDeserializer
import com.frontend.oportunia.data.remote.serializer.CompanyReviewDeserializer
import com.frontend.oportunia.data.remote.serializer.ExperienceDeserializer
import com.frontend.oportunia.data.remote.serializer.StudentDeserializer
import com.frontend.oportunia.data.remote.serializer.UserDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "http://10.0.2.2:8080/v1/"
    private const val DATE_FORMAT = "yyyy-MM-dd"


    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .apply { level = HttpLoggingInterceptor.Level.BODY }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        responseInterceptor: ResponseInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(responseInterceptor)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()


    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        // aqui irian los deserializadores
        .registerTypeAdapter(CompanyReviewDto::class.java, CompanyReviewDeserializer())
        .registerTypeAdapter(AbilityDto::class.java, AbilityDeserializer())
        .registerTypeAdapter(StudentDto::class.java, StudentDeserializer())
        .registerTypeAdapter(ExperienceDto::class.java, ExperienceDeserializer())
        .registerTypeAdapter(UserDto::class.java, UserDeserializer())
        .registerTypeAdapter(ExperienceDto::class.java, ExperienceDeserializer())
        .setDateFormat(DATE_FORMAT)
        .create()


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideCompanyService(retrofit: Retrofit): CompanyService =
        retrofit.create(CompanyService::class.java)


    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)


    @Provides
    @Singleton
    fun provideStudentService(retrofit: Retrofit): StudentService =
        retrofit.create(StudentService::class.java)


    @Provides
    @Singleton
    fun provideCompanyReviewService(retrofit: Retrofit): CompanyReviewService =
        retrofit.create(CompanyReviewService::class.java)


    @Provides
    @Singleton
    fun provideAbilityService(retrofit: Retrofit): AbilityService =
        retrofit.create(AbilityService::class.java)


    @Provides
    @Singleton
    fun provideExperienceService(retrofit: Retrofit): ExperienceService =
        retrofit.create(ExperienceService::class.java)


    @Provides
    @Singleton
    fun provideAdviceService(retrofit: Retrofit): AdviceService =
        retrofit.create(AdviceService::class.java)


    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideCurriculumService(retrofit: Retrofit): CurriculumService =
        retrofit.create(CurriculumService::class.java)


    @Provides
    @Singleton
    fun provideInterviewApiService(retrofit: Retrofit): InterviewService =
        retrofit.create(InterviewService::class.java)

    @Provides
    @Singleton
    fun provideQuizService(retrofit: Retrofit): QuizService =
        retrofit.create(QuizService::class.java)
}

