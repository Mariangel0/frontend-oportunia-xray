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





}