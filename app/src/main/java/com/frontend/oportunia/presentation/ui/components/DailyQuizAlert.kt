package com.frontend.oportunia.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes


@Composable
fun DailyQuizAlert(navController: NavController) {
    Card(
        modifier = Modifier.padding(horizontal = 25.dp, vertical = 20.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(bottom = 10.dp, top = 10.dp, start = 16.dp, end = 16.dp), verticalAlignment = Alignment.CenterVertically ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Información",
                modifier = Modifier.padding(end = 8.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
            Text(text = stringResource(id = R.string.daily_quiz), style = MaterialTheme.typography.bodyMedium)
        }
        Row(modifier = Modifier.align(Alignment.End).padding(bottom = 10.dp, top = 10.dp, start = 16.dp, end = 16.dp)) {
            Button(onClick = { navController.navigate(NavRoutes.QuizScreen.ROUTE)}
                , shape = MaterialTheme.shapes.small,) {
                Text(text = stringResource(id = R.string.button_Start))
            }
        }

    }
}
