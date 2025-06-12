package com.frontend.oportunia.data.di

import com.frontend.oportunia.data.repository.AbilityRepositoryImpl
import com.frontend.oportunia.data.repository.AdviceRepositoryImpl
import com.frontend.oportunia.data.repository.CompanyRepositoryImpl
import com.frontend.oportunia.data.repository.CompanyReviewRepositoryImpl
import com.frontend.oportunia.data.repository.CurriculumRepositoryImpl
import com.frontend.oportunia.data.repository.ExperienceRepositoryImpl
import com.frontend.oportunia.data.repository.IAAnalysisRepositoryImpl
import com.frontend.oportunia.data.repository.InterviewRepositoryImpl
import com.frontend.oportunia.data.repository.QuizRepositoryImpl
import com.frontend.oportunia.data.repository.StreakRepositoryImpl
import com.frontend.oportunia.data.repository.StudentRepositoryImpl
import com.frontend.oportunia.data.repository.UserRepositoryImpl
import com.frontend.oportunia.domain.repository.AbilityRepository
import com.frontend.oportunia.domain.repository.AdviceRepository
import com.frontend.oportunia.domain.repository.CompanyRepository
import com.frontend.oportunia.domain.repository.CompanyReviewRepository
import com.frontend.oportunia.domain.repository.CurriculumRepository
import com.frontend.oportunia.domain.repository.ExperienceRepository
import com.frontend.oportunia.domain.repository.IAAnalysisRepository
import com.frontend.oportunia.domain.repository.InterviewRepository
import com.frontend.oportunia.domain.repository.QuizRepository
import com.frontend.oportunia.domain.repository.StreakRepository
import com.frontend.oportunia.domain.repository.StudentRepository
import com.frontend.oportunia.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
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

    @Binds
    @Singleton
    abstract fun bindAdviceRepository(
        adviceRepositoryImpl: AdviceRepositoryImpl
    ): AdviceRepository

    @Binds
    @Singleton
    abstract fun bindInterviewRepository(
        interviewRepositoryImpl: InterviewRepositoryImpl
    ): InterviewRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        quizRepositoryImpl: QuizRepositoryImpl
    ): QuizRepository

    @Binds
    @Singleton
    abstract fun bindCurriculumRepository(
        curriculumRepositoryImpl:CurriculumRepositoryImpl
    ): CurriculumRepository

    @Binds
    @Singleton
    abstract fun bindIAAnalysisRepository(
        iaAnalysisRepositoryImpl:IAAnalysisRepositoryImpl
    ): IAAnalysisRepository

    @Binds
    @Singleton
    abstract fun bindStreakRepository(
        streakRepositoryImpl: StreakRepositoryImpl
    ): StreakRepository
}