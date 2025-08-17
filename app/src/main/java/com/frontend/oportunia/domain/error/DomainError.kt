package com.frontend.oportunia.domain.error

sealed class DomainError(
    override val message: String,
    override val cause: Throwable? = null
) : Exception(message, cause) {

    class UserError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class NetworkError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class MappingError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class UnknownError(
        message: String = "An unknown error occurred",
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class AdminError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class CompanyError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class CompanyReviewError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class CurriculumError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class EducationError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class ExperienceError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class IAAnalysisError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class InterviewError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class NotificationError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class PrivilegeError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class RoleError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class StreakError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class StudentError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

    class StudentProgressError(
        message: String,
        cause: Throwable? = null
    ) : DomainError(message, cause)

}