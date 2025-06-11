package com.frontend.oportunia.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.*
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.viewmodel.InterviewViewModel

@Composable
fun InterviewMenuScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel,
    interviewViewModel: InterviewViewModel,
) {
    val user by interviewViewModel.loggedUser.collectAsState()
    var jobPosition by remember { mutableStateOf("") }
    var selectedCompany by remember { mutableStateOf<String?>(null) }
    var selectedInterviewType by remember { mutableStateOf("General") }
    val companies by companyViewModel.companyList.collectAsState()

    Log.d("USERID", "$user")

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

            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Interview Chat",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            )

            Text(
                text = stringResource(R.string.interview_with_oportunia),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            InterviewJobField(
                value = jobPosition,
                onValueChange = { jobPosition = it },
                placeholder = stringResource(R.string.enter_job_position)
            )

            InterviewCompanyDropdown(
                companies = companies,
                selectedCompany = selectedCompany,
                onCompanySelected = { selectedCompany = it },
                placeholder = stringResource(R.string.select_company)
            )

            InterviewTypeSelector(
                selectedType = selectedInterviewType,
                onTypeSelected = { selectedInterviewType = it }
            )

            val context = LocalContext.current
            val initialMessage = context.getString(R.string.start_ai_interview)

            AcceptButton(
                text = stringResource(R.string.start_interview),
                enabled = jobPosition.isNotEmpty(),
                onClick = {
                    Log.d("InterviewMenu", "Start interview clicked")
                    val userId = user?.id ?: 6
                    Log.d("InterviewStart", "jobPosition: $jobPosition, type: $selectedInterviewType, msg: $initialMessage")

                    interviewViewModel.resetChat()
                    interviewViewModel.startInterview(
                        context = context,
                        studentId = userId,
                        jobPosition = jobPosition,
                        interviewType = selectedInterviewType,
                        initialMessage = initialMessage
                    )

                    navController.navigate(
                        NavRoutes.InterviewChat.createRoute(userId)
                    )
                }
            )
        }
    }
}
