package com.frontend.oportunia



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.frontend.oportunia.presentation.ui.components.BottomNavigationBar
import com.frontend.oportunia.presentation.ui.navigation.NavGraph
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes
import com.frontend.oportunia.presentation.ui.screens.admin.AdminMenuScreen
import com.frontend.oportunia.presentation.ui.screens.admin.ManageCompaniesScreen
import com.frontend.oportunia.presentation.ui.theme.OportunIATheme
import com.frontend.oportunia.presentation.viewmodel.AdviceViewModel
import com.frontend.oportunia.presentation.viewmodel.CompanyReviewViewModel
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.viewmodel.InterviewViewModel
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import com.frontend.oportunia.presentation.viewmodel.ProfileViewModel
import com.frontend.oportunia.presentation.viewmodel.QuizViewModel
import com.frontend.oportunia.presentation.viewmodel.RegisterViewModel
import com.frontend.oportunia.presentation.viewmodel.SkillsViewModel
import com.frontend.oportunia.presentation.viewmodel.CurriculumViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private  val companyViewModel: CompanyViewModel by viewModels ()


    private val loginViewModel: LoginViewModel by viewModels ()


    private val registerViewModel: RegisterViewModel by viewModels ()


    private val skillViewModel: SkillsViewModel by viewModels ()


    private val adviceViewModel: AdviceViewModel by viewModels ()

    private val curriculumViewModel: CurriculumViewModel by viewModels ()

    private val interviewViewModel: InterviewViewModel by viewModels ()

    private val quizViewModel: QuizViewModel by viewModels ()

    private val profileViewModel: ProfileViewModel by viewModels ()

    private val companyReviewViewModel: CompanyReviewViewModel by viewModels ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                Main(companyViewModel, loginViewModel, registerViewModel, skillViewModel, adviceViewModel, profileViewModel, companyReviewViewModel, interviewViewModel, quizViewModel, curriculumViewModel)
            }
        }
    }
}




@Composable
fun Main(
    companyViewModel: CompanyViewModel,
    loginViewModel: LoginViewModel,
    registerViewModel: RegisterViewModel,
    skillViewModel: SkillsViewModel,
    adviceViewModel: AdviceViewModel,
    profileViewModel: ProfileViewModel,
    companyReviewViewModel: CompanyReviewViewModel,
    interviewViewModel: InterviewViewModel,
    quizViewModel: QuizViewModel,
    curriculumViewModel: CurriculumViewModel
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val user by loginViewModel.loggedUser.collectAsState()
    val isAdmin = user?.roles?.any { it.name == "ADMIN" } == true

    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf(
                    NavRoutes.Login.ROUTE,
                    NavRoutes.Register.ROUTE,
                    NavRoutes.MainPage.ROUTE
                )
            ) {
                BottomNavigationBar(
                    navController = navController,
                    isAdmin = isAdmin
                )
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            companyViewModel = companyViewModel,
            loginViewModel = loginViewModel,
            registerViewModel = registerViewModel,
            skillsViewModel = skillViewModel,
            adviceViewModel = adviceViewModel,
            profileViewModel = profileViewModel,
            reviewViewModel = companyReviewViewModel,
            interviewViewModel = interviewViewModel,
            quizViewModel = quizViewModel,
            curriculumViewModel = curriculumViewModel
        )
    }
}

//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            OportunIATheme {
//                Scaffold(
//                ) { paddingValues ->
//                    AgregarEmpresaScreen(paddingValues)
//                }
//            }
//        }
//    }
//}
