package com.frontend.oportunia.presentation.ui.navigation


import com.frontend.oportunia.presentation.viewmodel.CurriculumViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.oportunia.presentation.ui.screens.AdviceScreen
import com.frontend.oportunia.presentation.ui.screens.CompanyScreen
import com.frontend.oportunia.presentation.ui.screens.CompanyTabsScreen
import com.frontend.oportunia.presentation.ui.screens.LoginScreen
import com.frontend.oportunia.presentation.ui.screens.MainScreen
import com.frontend.oportunia.presentation.ui.screens.MenuScreen
import com.frontend.oportunia.presentation.ui.screens.CurriculumScreen
import com.frontend.oportunia.presentation.ui.screens.PerfilScreen
import com.frontend.oportunia.presentation.ui.screens.RegisterScreen
import com.frontend.oportunia.presentation.ui.screens.SkillsScreen
import com.frontend.oportunia.presentation.viewmodel.AdviceViewModel
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import com.frontend.oportunia.presentation.viewmodel.RegisterViewModel
import com.frontend.oportunia.presentation.viewmodel.SkillsViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    skillsViewModel: SkillsViewModel,
    adviceViewModel: AdviceViewModel,
    curriculumViewModel: CurriculumViewModel

) {
    NavHost(navController, startDestination = NavRoutes.MainPage.ROUTE) {


        composable(NavRoutes.MainPage.ROUTE) {
            MainScreen(navController)
        }


        composable(NavRoutes.Login.ROUTE) {
            LoginScreen(paddingValues, loginViewModel, navController,
                onLoginSuccess = {
                    navController.navigate(NavRoutes.Menu.ROUTE) {
                        // Clear back stack so user can't navigate back to login screen
                        popUpTo(NavRoutes.Login.ROUTE) { inclusive = true }
                    }
                }
            )
        }


        composable(NavRoutes.Register.ROUTE) {
            RegisterScreen(paddingValues, registerViewModel, navController)
        }


        composable(NavRoutes.Menu.ROUTE) {
            MenuScreen( companyViewModel, paddingValues, navController, loginViewModel)
        }


        composable(NavRoutes.CompanyMenu.ROUTE) {
            CompanyScreen(paddingValues, companyViewModel, navController)
        }


        composable(NavRoutes.AdviceList.ROUTE) {
            AdviceScreen(navController, paddingValues, adviceViewModel)
        }


        composable(NavRoutes.Profile.ROUTE) {
            PerfilScreen(navController, paddingValues,loginViewModel)
        }


        composable(
            route = NavRoutes.CompanyDetail.ROUTE,
            arguments = listOf(navArgument(NavRoutes.CompanyDetail.ARG_COMP_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val compId = backStackEntry.arguments?.getLong(NavRoutes.CompanyDetail.ARG_COMP_ID) ?: 0L
            CompanyTabsScreen(
                companyId = compId,
                companyViewModel = companyViewModel,
                navController = navController,
                paddingValues = paddingValues
            )
        }




        composable(
            route = NavRoutes.SkillScreen.ROUTE,
            arguments = listOf(navArgument(NavRoutes.SkillScreen.ARG_STD_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val stdId = backStackEntry.arguments?.getLong(NavRoutes.SkillScreen.ARG_STD_ID) ?: 0L
            SkillsScreen(
                studentId = stdId,
                skillsViewModel = skillsViewModel,
                navController = navController,
                paddingValues = paddingValues
            )
        }


        composable(NavRoutes.CurriculumScreen.ROUTE) {
            CurriculumScreen(
                navController = navController,
                paddingValues = paddingValues,
                curriculumViewModel = curriculumViewModel
            )
        }

    }
}


