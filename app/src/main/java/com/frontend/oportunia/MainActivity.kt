package com.frontend.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

//import com.frontend.oportunia.data.datasource.StudentDataSourceImpl
import com.frontend.oportunia.data.mapper.CompanyMapper
import com.frontend.oportunia.data.mapper.StudentMapper
import com.frontend.oportunia.data.mapper.UserMapper
import com.frontend.oportunia.data.repository.CompanyRepositoryImpl
import com.frontend.oportunia.data.repository.StudentRepositoryImpl
//import com.frontend.oportunia.presentation.factory.CompanyViewModelFactory
import com.frontend.oportunia.presentation.factory.LoginViewModelFactory
import com.frontend.oportunia.presentation.ui.components.BottomNavigationBar
import com.frontend.oportunia.presentation.ui.navigation.NavGraph
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes
import com.frontend.oportunia.presentation.ui.theme.OportunIATheme
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


//class MainActivity : ComponentActivity() {
//
//    private val loginViewModel: LoginViewModel by viewModels {
//        val userMapper = UserMapper()
//        val studentMapper = StudentMapper(userMapper)
//        val studentDataSource = StudentDataSourceImpl(studentMapper)
//        val studentRepository = StudentRepositoryImpl(studentDataSource, studentMapper)
//        LoginViewModelFactory(studentRepository)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            OportunIATheme {
//                Main(loginViewModel)
//            }
//        }
//    }
//}
//
//@Composable
//fun Main(loginViewModel: LoginViewModel) {
//    Scaffold { paddingValues ->
//        LoginScreen(paddingValues = paddingValues, viewModel = loginViewModel)
//    }
//}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private  val companyViewModel: CompanyViewModel by viewModels ()

    private val loginViewModel: LoginViewModel by viewModels ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                Main(companyViewModel, loginViewModel)
            }
        }
    }
}


@Composable
fun Main(companyViewModel: CompanyViewModel, loginViewModel: LoginViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            // Solo mostrar el BottomNavigationBar si la ruta no es una de las que no quieres mostrar la barra
            if (currentRoute !in listOf(
                    NavRoutes.Login.ROUTE,
                    NavRoutes.Register.ROUTE,
                    NavRoutes.MainPage.ROUTE
                )) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            companyViewModel = companyViewModel,
            loginViewModel = loginViewModel
        )
    }
}