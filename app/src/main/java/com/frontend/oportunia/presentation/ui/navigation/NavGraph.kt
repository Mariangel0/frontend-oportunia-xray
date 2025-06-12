package com.frontend.oportunia.presentation.ui.navigation


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.oportunia.presentation.ui.screens.AddReviewScreen
import com.frontend.oportunia.presentation.ui.screens.AdviceScreen
import com.frontend.oportunia.presentation.ui.screens.CVAnalysisScreen
import com.frontend.oportunia.presentation.ui.screens.CompanyScreen
import com.frontend.oportunia.presentation.ui.screens.CompanyTabsScreen
import com.frontend.oportunia.presentation.ui.screens.CurriculumScreen
import com.frontend.oportunia.presentation.ui.screens.InterviewAnalysisScreen
import com.frontend.oportunia.presentation.ui.screens.InterviewChatScreen
import com.frontend.oportunia.presentation.ui.screens.InterviewMenuScreen
import com.frontend.oportunia.presentation.ui.screens.LoginScreen
import com.frontend.oportunia.presentation.ui.screens.MainScreen
import com.frontend.oportunia.presentation.ui.screens.MenuScreen
import com.frontend.oportunia.presentation.ui.screens.PerfilScreen
import com.frontend.oportunia.presentation.ui.screens.QuizScreen
import com.frontend.oportunia.presentation.ui.screens.RegisterScreen
import com.frontend.oportunia.presentation.ui.screens.SkillsScreen
import com.frontend.oportunia.presentation.ui.screens.admin.AdminMenuScreen
import com.frontend.oportunia.presentation.ui.screens.admin.ManageCompaniesScreen
import com.frontend.oportunia.presentation.ui.screens.admin.UserListScreen
import com.frontend.oportunia.presentation.viewmodel.AdviceViewModel
import com.frontend.oportunia.presentation.viewmodel.CompanyReviewViewModel
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.viewmodel.CurriculumViewModel
import com.frontend.oportunia.presentation.viewmodel.IAAnalysisViewModel
import com.frontend.oportunia.presentation.viewmodel.InterviewViewModel
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import com.frontend.oportunia.presentation.viewmodel.ProfileViewModel
import com.frontend.oportunia.presentation.viewmodel.QuizViewModel
import com.frontend.oportunia.presentation.viewmodel.RegisterViewModel
import com.frontend.oportunia.presentation.viewmodel.SkillsViewModel
import com.frontend.oportunia.presentation.viewmodel.StreakViewModel
import com.frontend.oportunia.presentation.viewmodel.UserViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    skillsViewModel: SkillsViewModel,
    adviceViewModel: AdviceViewModel,
    profileViewModel: ProfileViewModel,
    reviewViewModel: CompanyReviewViewModel,
    curriculumViewModel: CurriculumViewModel,
    interviewViewModel: InterviewViewModel,
    quizViewModel: QuizViewModel,
    userViewModel: UserViewModel,
    iAAnalysisViewModel: IAAnalysisViewModel,
    streakViewModel: StreakViewModel
) {
    NavHost(navController, startDestination = NavRoutes.MainPage.ROUTE) {


        composable(NavRoutes.MainPage.ROUTE) {
            MainScreen(navController)
        }


        composable(NavRoutes.Login.ROUTE) {
            LoginScreen(
                paddingValues = paddingValues,
                viewModel = loginViewModel,
                navController = navController,
                onLoginSuccess = {
                    // Ya no navegues aquí. La navegación se hace dentro de LoginScreen.
                }
            )
        }

                composable(NavRoutes.Register.ROUTE) {
            RegisterScreen(paddingValues, registerViewModel, navController)
        }


        composable(NavRoutes.Menu.ROUTE) {
            MenuScreen( companyViewModel, paddingValues, navController, loginViewModel, streakViewModel)
        }


        composable(NavRoutes.CompanyMenu.ROUTE) {
            CompanyScreen(paddingValues, companyViewModel, navController)
        }


        composable(NavRoutes.AdviceList.ROUTE) {
            AdviceScreen(navController, paddingValues, adviceViewModel)
        }

        composable(NavRoutes.Profile.ROUTE) {
            PerfilScreen(
                navController = navController,
                paddingValues = paddingValues,
                profileViewModel = profileViewModel
            )
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
                paddingValues = paddingValues,
                reviewViewModel = reviewViewModel,
            )
        }

        composable(
            route = NavRoutes.CompanyReview.ROUTE,
            arguments = listOf(navArgument(NavRoutes.CompanyReview.ARG_COMP_ID) {
                type = NavType.LongType
            })
        ){ backStackEntry ->
            val compId = backStackEntry.arguments?.getLong(NavRoutes.CompanyReview.ARG_COMP_ID) ?: 0L
            AddReviewScreen(
                companyId = compId,
                navController = navController,
                paddingValues = paddingValues,
                reviewViewModel = reviewViewModel
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


        composable(
            route = NavRoutes.CurriculumScreen.ROUTE,
            arguments = listOf(navArgument(NavRoutes.CurriculumScreen.ARG_STD_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getLong(NavRoutes.CurriculumScreen.ARG_STD_ID) ?: 0L
            CurriculumScreen(
                studentId = studentId,
                navController = navController,
                paddingValues = paddingValues,
                curriculumViewModel = curriculumViewModel
            )
        }

        composable(
            route = NavRoutes.InterviewScreen.ROUTE,
            arguments = listOf(navArgument(NavRoutes.InterviewScreen.ARG_INT_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val stdId = backStackEntry.arguments?.getLong(NavRoutes.InterviewScreen.ARG_INT_ID) ?: 0L
            InterviewMenuScreen(
                companyViewModel = companyViewModel,
                navController = navController,
                paddingValues = paddingValues,
                interviewViewModel = interviewViewModel
            )
        }

        composable(
            route = NavRoutes.InterviewChat.ROUTE,
            arguments = listOf(navArgument(NavRoutes.InterviewChat.ARG_INC_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val stdId = backStackEntry.arguments?.getLong(NavRoutes.InterviewChat.ARG_INC_ID) ?: 0L
            InterviewChatScreen(
                navController = navController,
                paddingValues = paddingValues,
                interviewViewModel = interviewViewModel,
                studentId = stdId
            )
        }

        composable(
            route = NavRoutes.QuizScreen.ROUTE,
            arguments = listOf(navArgument(NavRoutes.QuizScreen.ARG_QU_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val stdId = backStackEntry.arguments?.getLong(NavRoutes.QuizScreen.ARG_QU_ID) ?: 0L
            QuizScreen(
                navController = navController,
                paddingValues = paddingValues,
                quizViewModel = quizViewModel,
                streakViewModel = streakViewModel,
                studentId = stdId
            )
        }

        composable(NavRoutes.CVAnalysis.ROUTE) {
            CVAnalysisScreen(
                navController = navController,
                viewModel = curriculumViewModel
            )
        }

        composable(NavRoutes.AdminMenu.ROUTE) {
            AdminMenuScreen(
                navController = navController,
                paddingValues = paddingValues,
                viewModel = loginViewModel,
            )
        }

        composable(NavRoutes.ManageCompanies.ROUTE) {
            ManageCompaniesScreen(
                navController = navController,
                paddingValues = paddingValues,
                companyViewModel = companyViewModel,
            )
        }

        composable(NavRoutes.UserScreen.ROUTE) {
            UserListScreen(
                navController = navController,
                paddingValues = paddingValues,
                userViewModel = userViewModel
            )
        }

        composable(
            route = NavRoutes.IAAnalysisScreen.ROUTE,
            arguments = listOf(navArgument(NavRoutes.IAAnalysisScreen.ARG_IAA_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val interviewId = backStackEntry.arguments?.getLong(NavRoutes.IAAnalysisScreen.ARG_IAA_ID) ?: 0L
            InterviewAnalysisScreen(
                navController = navController,
                viewModel = iAAnalysisViewModel,
                interviewId = interviewId
            )
        }

        composable(NavRoutes.AdminMenu.ROUTE) {
            AdminMenuScreen(
                navController = navController,
                paddingValues = paddingValues,
                viewModel = loginViewModel,
            )
        }

        composable(NavRoutes.ManageCompanies.ROUTE) {
            ManageCompaniesScreen(
                navController = navController,
                paddingValues = paddingValues,
                companyViewModel = companyViewModel,
            )
        }

        composable(NavRoutes.UserScreen.ROUTE) {
            UserListScreen(
                navController = navController,
                paddingValues = paddingValues,
                userViewModel = userViewModel
            )
        }

    }
}


