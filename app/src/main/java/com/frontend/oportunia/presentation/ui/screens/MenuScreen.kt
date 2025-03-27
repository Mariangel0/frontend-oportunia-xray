package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.CustomCardWithText
import com.frontend.oportunia.presentation.ui.components.DailyQuizAlert
import com.frontend.oportunia.presentation.ui.components.StreakCalendar

@Composable
fun MenuScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        DailyQuizAlert(onClick = {})// Alerta de quiz diario
        StreakCalendar() // Racha (Calendario de días)
        Learning()
        CompaniesCarousel()
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
fun CompaniesCarousel() {
    CustomCardWithText(title = "Empresas") {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Carousel Content (LazyRow for scrolling)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp, top = 20.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp) // Spacing between items
            ) {
                items(8) { index -> // Example of 5 items (companies)
                    CompanyIcon(
                        icon = Icons.Filled.ThumbUp,
                        contentDescription = "Company $index"
                    )
                }
            }

        }
    }
}

@Composable
fun CompanyIcon(
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .size(60.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = MaterialTheme.shapes.medium
            )
            .padding(15.dp),
        tint = MaterialTheme.colorScheme.primary
    )
}
