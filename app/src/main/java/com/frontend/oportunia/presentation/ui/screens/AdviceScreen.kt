package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.AdviceCard
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout

@Composable
fun AdviceScreen(navController: NavController, paddingValues: PaddingValues) {
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

            val consejos = listOf(
                "¿Por qué es importante investigar la empresa antes de aplicar?" to
                        "Conocer la empresa te ayuda a entender su cultura, valores y expectativas...",
                "¿Cómo hacer que tu CV destaque entre los demás?" to
                        "Destaca logros concretos, usa palabras clave de la oferta y adapta tu CV a la empresa.",
                "¿Por qué es importante investigar la empresa antes de aplicar?" to
                        "Conocer la empresa te ayuda a entender su cultura, valores y expectativas...",
                "¿Cómo hacer que tu CV destaque entre los demás?" to
                        "Destaca logros concretos, usa palabras clave de la oferta y adapta tu CV a la empresa.",
                "¿Por qué es importante investigar la empresa antes de aplicar?" to
                        "Conocer la empresa te ayuda a entender su cultura, valores y expectativas...",
                "¿Cómo hacer que tu CV destaque entre los demás?" to
                        "Destaca logros concretos, usa palabras clave de la oferta y adapta tu CV a la empresa.",
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(consejos) { (front, back) ->
                    AdviceCard(frontText = front, backText = back)
                }
            }
        }
    }
}
