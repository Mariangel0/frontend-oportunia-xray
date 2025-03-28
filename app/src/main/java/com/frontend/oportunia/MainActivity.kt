package com.frontend.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.frontend.oportunia.data.datasource.CompanyDataSourceImpl
import com.frontend.oportunia.data.mapper.CompanyMapper
import com.frontend.oportunia.data.repository.CompanyRepositoryImpl
import com.frontend.oportunia.presentation.factory.CompanyViewModelFactory
import com.frontend.oportunia.presentation.ui.components.BottomNavigationBar
import com.frontend.oportunia.presentation.ui.navigation.NavGraph
import com.frontend.oportunia.presentation.ui.theme.OportunIATheme
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel


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

class MainActivity : ComponentActivity() {
    private  val companyViewModel: CompanyViewModel by viewModels {
        val companyMapper = CompanyMapper()
        val companyDataSource = CompanyDataSourceImpl(companyMapper)
        val companyRepository = CompanyRepositoryImpl(companyDataSource, companyMapper)
        CompanyViewModelFactory(companyRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OportunIATheme {
                Main(companyViewModel)
            }
        }
    }
}


@Composable
fun Main(companyViewModel: CompanyViewModel) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
            )
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            paddingValues = paddingValues,
            companyViewModel = companyViewModel
        )
    }
}