package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.AcceptButton
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.components.InterviewCompanyDropdown
import com.frontend.oportunia.presentation.ui.components.InterviewJobField
import com.frontend.oportunia.presentation.ui.components.InterviewTypeSelector
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel

@Composable
fun InterviewMenuScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel
) {
    var jobPosition by remember { mutableStateOf("") }
    var selectedCompany by remember { mutableStateOf<String?>(null) }
    var selectedInterviewType by remember { mutableStateOf("General") }

    val companies by companyViewModel.companyList.collectAsState()

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.interview_menu),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // Icono de chat
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Interview Chat",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )

            // Título
            Text(
                text = stringResource(R.string.interview_with_oportunia),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(2.dp))

            // Campo de puesto
            InterviewJobField(
                value = jobPosition,
                onValueChange = { jobPosition = it },
                placeholder = stringResource(R.string.enter_job_position)
            )

            // Dropdown de empresa
            InterviewCompanyDropdown(
                companies = companies,
                selectedCompany = selectedCompany,
                onCompanySelected = { selectedCompany = it },
                placeholder = stringResource(R.string.select_company)
            )

            // Selector de tipo de entrevista
            InterviewTypeSelector(
                selectedType = selectedInterviewType,
                onTypeSelected = { selectedInterviewType = it }
            )

            AcceptButton(
                text = stringResource(R.string.start_interview),
                enabled = jobPosition.isNotEmpty() && selectedCompany != null,
                onClick = {
                    navController.navigate(
                        NavRoutes.InterviewChat.createRoute(
                            studentId = 0,
                            jobPosition = jobPosition,
                            companyName = selectedCompany!!,
                           interviewType = selectedInterviewType
                        )
                    )
                }
            )
        }
    }
}