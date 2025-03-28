package com.frontend.oportunia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.frontend.oportunia.presentation.ui.screens.MenuScreen
import com.frontend.oportunia.presentation.ui.theme.OportunIATheme
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import androidx.activity.viewModels
import com.frontend.oportunia.data.mapper.CompanyMapper
import com.frontend.oportunia.data.repository.CompanyRepositoryImpl
import com.frontend.oportunia.data.datasource.CompanyDataSourceImpl
import com.frontend.oportunia.presentation.factory.CompanyViewModelFactory

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
                MenuScreen(companyViewModel)
            }
        }
    }
}

