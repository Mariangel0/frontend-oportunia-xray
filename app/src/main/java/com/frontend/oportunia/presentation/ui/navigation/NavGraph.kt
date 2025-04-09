package com.frontend.oportunia.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.frontend.oportunia.presentation.ui.screens.MenuScreen
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.oportunia.presentation.ui.screens.AdviceScreen
import com.frontend.oportunia.presentation.ui.screens.CompanyDetailScreen
import com.frontend.oportunia.presentation.ui.screens.PerfilScreen
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.ui.screens.CompanyScreen
import com.frontend.oportunia.presentation.ui.screens.LoginScreen
import com.frontend.oportunia.presentation.ui.screens.MainScreen
import com.frontend.oportunia.presentation.ui.screens.RegisterScreen
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import com.frontend.oportunia.presentation.viewmodel.RegisterViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel,
    loginViewModel: LoginViewModel,
) {
    NavHost(navController, startDestination = NavRoutes.MainPage.ROUTE) {

        composable(NavRoutes.MainPage.ROUTE) {
            MainScreen(navController)
        }

        composable(NavRoutes.Login.ROUTE) {
            LoginScreen(paddingValues, loginViewModel, navController)
        }

        composable(NavRoutes.Register.ROUTE) {
            RegisterScreen(paddingValues, viewModel(), navController)
        }

        composable(NavRoutes.Menu.ROUTE) {
            MenuScreen( companyViewModel, paddingValues, navController)
        }

        composable(NavRoutes.CompanyMenu.ROUTE) {
            CompanyScreen(paddingValues, companyViewModel, navController)
        }

        composable(NavRoutes.AdviceList.ROUTE) {
            AdviceScreen(navController, paddingValues)
        }

        composable(NavRoutes.Profile.ROUTE) {
            PerfilScreen(navController, paddingValues)
        }

        composable(
            route = NavRoutes.CompanyDetail.ROUTE,
            arguments = listOf(navArgument(NavRoutes.CompanyDetail.ARG_COMP_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val compId = backStackEntry.arguments?.getLong(NavRoutes.CompanyDetail.ARG_COMP_ID) ?: 0L
            CompanyDetailScreen(
                companyId = compId,
                companyViewModel = companyViewModel,
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}



