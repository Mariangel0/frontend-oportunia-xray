package com.frontend.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import com.frontend.oportunia.presentation.ui.screens.admin.AdminMenuScreen
import com.frontend.oportunia.presentation.ui.screens.admin.AgregarEmpresaScreen
import com.frontend.oportunia.presentation.ui.theme.OportunIATheme

//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    private  val companyViewModel: CompanyViewModel by viewModels ()
//
//    private val loginViewModel: LoginViewModel by viewModels ()
//
//    private val registerViewModel: RegisterViewModel by viewModels ()
//
//    private val skillViewModel: SkillsViewModel by viewModels ()
//
//    private val adviceViewModel: AdviceViewModel by viewModels ()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            OportunIATheme {
//                Main(companyViewModel, loginViewModel, registerViewModel, skillViewModel, adviceViewModel)
//            }
//        }
//    }
//}
//
//
//@Composable
//fun Main(companyViewModel: CompanyViewModel, loginViewModel: LoginViewModel, registerViewModel: RegisterViewModel, skillViewModel: SkillsViewModel, adviceViewModel: AdviceViewModel) {
//    val navController = rememberNavController()
//    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//
//    Scaffold(
//        bottomBar = {
//            // Solo mostrar el BottomNavigationBar si la ruta no es una de las que no quieres mostrar la barra
//            if (currentRoute !in listOf(
//                    NavRoutes.Login.ROUTE,
//                    NavRoutes.Register.ROUTE,
//                    NavRoutes.MainPage.ROUTE
//                )) {
//                BottomNavigationBar(navController = navController)
//            }
//        }
//    ) { paddingValues ->
//        NavGraph(
//            navController = navController,
//            paddingValues = paddingValues,
//            companyViewModel = companyViewModel,
//            loginViewModel = loginViewModel,
//            registerViewModel = registerViewModel,
//            skillsViewModel = skillViewModel,
//            adviceViewModel = adviceViewModel
//        )
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                Scaffold(
                ) { paddingValues ->
                    AgregarEmpresaScreen(paddingValues)
                }
            }
        }
    }
}
