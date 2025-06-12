package com.frontend.oportunia.presentation.ui.navigation

sealed class NavRoutes {
    data object Menu : NavRoutes() {
        const val ROUTE = "menu"
    }

    data object AdviceList : NavRoutes() {
        const val ROUTE = "adviceList"
    }

    data object Profile : NavRoutes() {
        const val ROUTE = "profile"
    }
    data object CompanyMenu : NavRoutes() {
        const val ROUTE = "companyMenu"
    }
    data object Login : NavRoutes() {
        const val ROUTE = "login"
    }

    data object Register : NavRoutes() {
        const val ROUTE = "register"
    }
    data object MainPage : NavRoutes() {
        const val ROUTE = "main"
    }

    data object CompanyDetail : NavRoutes() {
        const val ROUTE = "companyDetail/{companyId}"
        const val ARG_COMP_ID = "companyId"

        fun createRoute(companyId: Long) = "companyDetail/$companyId"
    }

    data object SkillScreen : NavRoutes() {
        const val ROUTE = "skills/{studentId}"
        const val ARG_STD_ID = "studentId"

        fun createRoute(studentId: Long) = "skills/$studentId"
    }

    data object CurriculumScreen : NavRoutes() {
        const val ROUTE = "curriculum/{studentId}"
        const val ARG_STD_ID = "studentId"

        fun createRoute(studentId: Long) = "curriculum/$studentId"
    }

    data object InterviewScreen : NavRoutes() {
        const val ROUTE = "interview/{studentId}"
        const val ARG_INT_ID = "studentId"

        fun createRoute(studentId: Long) = "interview/$studentId"
    }

    data object InterviewChat : NavRoutes() {
        const val ROUTE = "interview_chat/{studentId}"
        const val ARG_INC_ID = "studentId"
        fun createRoute(studentId: Long) = "interview_chat/$studentId"
    }


    data object CompanyReview : NavRoutes() {
        const val ROUTE = "companyReview/{companyId}"
        const val ARG_COMP_ID = "companyId"

        fun createRoute(companyId: Long) = "companyReview/$companyId"
    }

    data object QuizScreen : NavRoutes() {
        const val ROUTE = "quiz/{studentId}"
        const val ARG_QU_ID = "studentId"

        fun createRoute(studentId: Long) = "quiz/$studentId"
    }

    data object CVAnalysis : NavRoutes() {
        const val ROUTE = "cv_analysis"
    }

    data object IAAnalysisScreen : NavRoutes() {
        const val ROUTE = "iaAnalyses/{interviewId}"
        const val ARG_IAA_ID = "interviewId"

        fun createRoute(interviewId: Long) = "iaAnalyses/$interviewId"
    }

}