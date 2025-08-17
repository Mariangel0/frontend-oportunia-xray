package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.AdviceCard
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.AdviceViewModel

@Composable
fun AdviceScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    adviceViewModel: AdviceViewModel,
) {

    LaunchedEffect(Unit) {
        adviceViewModel.loadAllAdvices()
    }

    val advices by adviceViewModel.adviceList.collectAsState()

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.advice),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Text(
                text = stringResource(R.string.advice_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(R.string.advice_subtitle),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (advices.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_data),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(advices) { advice ->
                        AdviceCard(frontText = advice.question, backText = advice.answer)
                    }
                }
            }
        }
    }
}
