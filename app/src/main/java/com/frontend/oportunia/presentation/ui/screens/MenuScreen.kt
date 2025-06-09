package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.CustomCardWithText
import com.frontend.oportunia.presentation.ui.components.DailyQuizAlert
import com.frontend.oportunia.presentation.ui.components.StreakCalendar
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel


@Composable
fun MenuScreen(
    companyViewModel: CompanyViewModel,
    paddingValues: PaddingValues,
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    val user by loginViewModel.loggedUser.collectAsState()
    val username = user?.firstName ?: "Usuario"

    Column(modifier = Modifier.fillMaxSize()) {
        MainLayout(
            paddingValues = paddingValues,
            headerType = HeaderType.WELCOME,
            username = username,
        ) {
            DailyQuizAlert(navController = navController, studentId = user?.id ?: 1)
            StreakCalendar()
            Learning(navController = navController, studentId = user?.id ?: -1L)
            CompaniesCarousel(companyViewModel = companyViewModel, navController = navController)
        }
    }
}

@Composable
fun Learning(navController: NavController, studentId: Long) {
    CustomCardWithText(
        title = stringResource(id = R.string.learning)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LearnItem(
                iconResId = R.drawable.icon_icon27,
                text = "Entrevistas",
                onClick = { navController.navigate(NavRoutes.InterviewScreen.createRoute(studentId)) }
            )
            LearnItem(
                iconResId = R.drawable.icon_curriculum,
                text = "Curriculum",
                onClick = { navController.navigate(NavRoutes.CurriculumScreen.ROUTE) }
            )
            LearnItem(
                iconResId = R.drawable.icon_shield_ban,
                text = "Habilidades",
                onClick = { navController.navigate(NavRoutes.SkillScreen.createRoute(studentId)) }
            )
        }
    }
}

@Composable
fun LearnItem(
    iconResId: Int,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = text,
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun CompaniesCarousel(companyViewModel: CompanyViewModel, navController: NavController) {
    val companies by companyViewModel.companyList.collectAsState()

    LaunchedEffect(Unit) {
        companyViewModel.loadAllCompanies()
    }

    CustomCardWithText(
        title = stringResource(id = R.string.companies),
    ) {
        Box(modifier = Modifier.fillMaxWidth().clickable {
            navController.navigate(NavRoutes.CompanyMenu.ROUTE)
        }) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(companies) { company ->
                    CompanyIcon(name = company.name)
                }
            }
        }
    }
}

@Composable
fun CompanyIcon(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
