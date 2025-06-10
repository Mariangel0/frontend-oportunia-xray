package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.oportunia.presentation.viewmodel.CurriculumViewModel
import com.frontend.oportunia.presentation.viewmodel.AnalysisState

@Composable
fun CVAnalysisScreen(
    navController: NavController,
    viewModel: CurriculumViewModel
) {
    val state by viewModel.analysisState.collectAsState()

    when (val currentState = state) {
        is AnalysisState.Success -> {
            val comentarios = currentState.response.comentarios
            val recomendaciones = currentState.response.recomendaciones
            val score = currentState.response.score

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFE0D7FF))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                    Text("Análisis de currículum", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
                }

                Column(modifier = Modifier.padding(24.dp)) {
                    Text("${score}%", fontSize = 36.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Text("DE 100%", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }

                Divider()

                Text(
                    text = "Comentario general",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                )
                Text(
                    text = comentarios,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Recomendaciones",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(recomendaciones) { recomendacion ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color(0xFF7C4DFF),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(20.dp)
                            )
                            Text(text = recomendacion, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFEDE7F6))
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("¿Quieres ver todos los problemas de tu currículum?", fontWeight = FontWeight.SemiBold)
                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF))
                    ) {
                        Text("Mejorar plan", color = Color.White)
                    }
                }
            }
        }

        is AnalysisState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AnalysisState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${currentState.message}")
            }
        }

        else -> {}
    }
}