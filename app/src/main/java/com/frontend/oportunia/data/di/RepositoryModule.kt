package com.frontend.oportunia.data.di

import com.frontend.oportunia.data.repository.AbilityRepositoryImpl
import com.frontend.oportunia.data.repository.CompanyRepositoryImpl
import com.frontend.oportunia.data.repository.CompanyReviewRepositoryImpl
import com.frontend.oportunia.data.repository.ExperienceRepositoryImpl
import com.frontend.oportunia.data.repository.StudentRepositoryImpl
import com.frontend.oportunia.data.repository.UserRepositoryImpl
import com.frontend.oportunia.domain.repository.AbilityRepository
import com.frontend.oportunia.domain.repository.CompanyRepository
import com.frontend.oportunia.domain.repository.CompanyReviewRepository
import com.frontend.oportunia.domain.repository.ExperienceRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import com.frontend.oportunia.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl
    ): CompanyRepository

    @Binds
    @Singleton
    abstract fun bindStudentRepository(
        studentRepositoryImpl: StudentRepositoryImpl
    ): StudentRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindCompanyReviewRepository(
        companyReviewRepositoryImpl: CompanyReviewRepositoryImpl
    ): CompanyReviewRepository

    @Binds
    @Singleton
    abstract fun bindAbilityRepository(
        abilityRepositoryImpl: AbilityRepositoryImpl
    ): AbilityRepository

    @Binds
    @Singleton
    abstract fun bindExperienceRepository(
        experienceRepositoryImpl: ExperienceRepositoryImpl
    ): ExperienceRepository


}