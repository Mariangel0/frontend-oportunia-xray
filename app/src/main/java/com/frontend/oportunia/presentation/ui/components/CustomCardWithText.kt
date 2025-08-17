package com.frontend.oportunia.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomCardWithText(
    title: String,
    content: @Composable () -> Unit // Esta función de contenido es la que cambiará según se use
) {
    Column(
        modifier = Modifier.padding(horizontal = 25.dp, vertical = 20.dp)
    ) {

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.background, MaterialTheme.shapes.large),
            colors = CardDefaults.cardColors(Color.White),
            elevation = CardDefaults.cardElevation(2.dp),
            shape = MaterialTheme.shapes.large
        ) {
            // Aquí pasas el contenido que cambia
            content()
        }
    }
}
