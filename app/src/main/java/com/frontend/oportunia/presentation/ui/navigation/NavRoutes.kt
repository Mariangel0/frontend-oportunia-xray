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

}