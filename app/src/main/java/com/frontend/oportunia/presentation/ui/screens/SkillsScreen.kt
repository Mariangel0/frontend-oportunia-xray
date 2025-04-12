package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.domain.model.Experience
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import com.frontend.oportunia.presentation.viewmodel.SkillsViewModel

@Composable
fun SkillsScreen(
    studentId : Long,
    navController: NavController,
    paddingValues: PaddingValues,
    skillsViewModel: SkillsViewModel,
) {
    LaunchedEffect(studentId) {
        skillsViewModel.getAbilitiesForStudent(studentId )
        skillsViewModel.getStudentById(studentId)
    }

    val student by skillsViewModel.loggedStudent.collectAsState()
    val abilities by skillsViewModel.abilityList.collectAsState()
   // val experiences by loginViewModel.experiences.collectAsState()
    val role = if (student != null) stringResource(R.string.student) else stringResource(R.string.adminastror)

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.skills),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Perfil
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = student?.user?.firstName + " " + (student?.user?.lastName ?: ""),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = role,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Habilidades blandas
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.soft_skills),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                if (abilities.isEmpty()) {
                    Text(
                        text = stringResource(R.string.no_skills),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                } else {
                    abilities.forEach { ability ->
                        SkillProgressBar(
                            skillName = ability.name,
                            progress = 0.5f
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


//            Column(modifier = Modifier.fillMaxWidth()) {
//                Text(
//                    text = stringResource(R.string.experience_title),
//                    style = MaterialTheme.typography.titleLarge,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//
//                if (experiences.isEmpty()) {
//                    Text(
//                        text = stringResource(R.string.no_experience),
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.Gray
//                    )
//                } else {
//                    val groupedExperiences = experiences
//                        .sortedByDescending { it.year }
//                        .groupBy { it.year }
//
//                    val companyNames = mapOf(
//                        1001L to "Google",
//                        1002L to "Microsoft",
//                        1003L to "Apple",
//                        1004L to "Amazon"
//                    )
//
//                    groupedExperiences.forEach { (_, exps) ->
//                        exps.forEach { experience ->
//                            ExperienceCard(
//                                experience = experience,
//                                companyName = companyNames[experience.company] ?: "${experience.company}"
//                            )
//                            Spacer(modifier = Modifier.height(12.dp))
//                        }
//                    }
//                }
//            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

@Composable
fun ExperienceCard(
    experience: Experience,
    companyName: String,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = experience.role,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = experience.year.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = companyName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SkillProgressBar(skillName: String, progress: Float) {
    Column {
        Text(
            text = skillName,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            )
        }
    }
}

