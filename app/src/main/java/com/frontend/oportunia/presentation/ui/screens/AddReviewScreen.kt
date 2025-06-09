package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.viewmodel.CompanyReviewViewModel

@Composable
fun AddReviewScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    companyId: Long,
    reviewViewModel: CompanyReviewViewModel
) {
    // 1️⃣ Collect UI state from ViewModel
    val user by reviewViewModel.loggedUser.collectAsState()
    val comment by reviewViewModel.comment.collectAsState()
    val rating  by reviewViewModel.rating.collectAsState()
    val errorMsg by reviewViewModel.error.collectAsState()
    val showError by reviewViewModel.showErrorDialog.collectAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Tu opinión nos ayuda a mejorar",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = comment,
                onValueChange = { reviewViewModel.comment.value = it },
                label = { Text("Comentario") },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(Modifier.height(24.dp))

            Text("Calificación", style = MaterialTheme.typography.bodyMedium)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                repeat(5) { index ->
                    Icon(
                        painter = painterResource(
                            id = if (index < rating) R.drawable.icon_star_full else R.drawable.icon_star
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                reviewViewModel.rating.value = (index + 1).toFloat()
                            }
                            .padding(end = 4.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Button(
                onClick = {
                    reviewViewModel.submitReview(
                        companyId  = companyId,
                    ) {
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("Enviar Reseña", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }

    // 2️⃣ Error dialog
    if (showError) {
        AlertDialog(
            onDismissRequest = { reviewViewModel.clearError() },
            confirmButton = {
                TextButton(onClick = { reviewViewModel.clearError() }) {
                    Text("OK")
                }
            },
            title = { Text("Error") },
            text = { Text(errorMsg ?: "Ha ocurrido un error") }
        )
    }
}
