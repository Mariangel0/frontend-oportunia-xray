package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.CustomCardWithText
import com.frontend.oportunia.presentation.ui.components.DailyQuizAlert
import com.frontend.oportunia.presentation.ui.components.StreakCalendar
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout


@Composable
fun MenuScreen(
    companyViewModel: CompanyViewModel,
    paddingValues: PaddingValues
) {
    Column(modifier = Modifier.fillMaxSize()) {
        MainLayout(
            paddingValues = paddingValues,
            headerType = HeaderType.WELCOME,
            username = "Juan",
        ) {
            DailyQuizAlert(onClick = {})// Alerta de quiz diario
            StreakCalendar() // Racha (Calendario de días)
            Learning()
            CompaniesCarousel(companyViewModel = companyViewModel)
        }
    }
}

@Composable
fun Learning() {
    // Usar el composable reutilizable
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
                icon = Icons.Outlined.AccountBox,
                text = "Entrevistas"
            )
            LearnItem(
                icon = Icons.AutoMirrored.Outlined.List,
                text = "Curriculum"
            )
            LearnItem(
                icon = Icons.Outlined.Build,
                text = "Habilidades"
            )

        }
    }
}

@Composable
fun LearnItem(
    icon: ImageVector,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
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
fun CompaniesCarousel(companyViewModel: CompanyViewModel) {
    val companies by companyViewModel.companyList.collectAsState()

    LaunchedEffect(Unit) {
        companyViewModel.loadAllCompanies()
    }

    CustomCardWithText(title = "Empresas") {
        Box(modifier = Modifier.fillMaxWidth()) {
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
            text = name.toString(), // muestra la inicial
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

