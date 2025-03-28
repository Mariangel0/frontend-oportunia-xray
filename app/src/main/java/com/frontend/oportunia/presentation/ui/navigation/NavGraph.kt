package com.frontend.oportunia.presentation.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import com.frontend.oportunia.presentation.ui.screens.MenuScreen
import androidx.navigation.compose.composable
import com.frontend.oportunia.presentation.ui.screens.AdviceScreen
import com.frontend.oportunia.presentation.ui.screens.PerfilScren
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel
) {
    NavHost(navController, startDestination = NavRoutes.Menu.ROUTE){

        composable(NavRoutes.Menu.ROUTE) {
            MenuScreen(companyViewModel, paddingValues)
        }
        composable(NavRoutes.AdviceList.ROUTE) {
            AdviceScreen(navController, paddingValues)
        }
        composable(NavRoutes.Profile.ROUTE) {
            PerfilScren(navController, paddingValues)
        }

    }
}