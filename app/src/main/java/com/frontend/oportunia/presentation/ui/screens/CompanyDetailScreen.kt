package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.domain.model.CompanyReview
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue


@Composable
fun CompanyTabsScreen(
    companyId: Long,
    companyViewModel: CompanyViewModel,
    navController: NavController,
    paddingValues: PaddingValues
) {
    val tabTitles = listOf("Reseñas", "Información")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(companyId) {
        companyViewModel.loadCompanyById(companyId)
        companyViewModel.getReviewsForCompany(companyId)
    }

    val selectedCompany by companyViewModel.selectedCompany.collectAsState()

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = selectedCompany?.name ?: stringResource(id = R.string.reviews),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            // Tabs
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedTabIndex) {
                0 -> CompanyReviewsSection(companyViewModel)
                1 -> CompanyInformationSection(companyViewModel)
            }
        }
    }
}


@Composable
fun CompanyReviewsSection(companyViewModel: CompanyViewModel) {
    val reviews by companyViewModel.companyReviewsList.collectAsState()
    val averageRating = if (reviews.isNotEmpty()) reviews.map { it.rating }.average().toFloat() else 0f

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "%.1f".format(averageRating),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${reviews.size} reseñas",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            RatingStars(
                rating = averageRating,
                starFilled = painterResource(id = R.drawable.icon_star_full),
                starBorder = painterResource(id = R.drawable.icon_star),
                starHalf = painterResource(id = R.drawable.icon_star_half)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        reviews.forEach { review ->
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                ReviewCard(review)
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
                )
            }
        }
    }
}

@Composable
fun CompanyInformationSection(companyViewModel: CompanyViewModel) {
    val company by companyViewModel.selectedCompany.collectAsState()

    company?.let {
        Column {
            SectionTitle("Información")
            InfoRow(label = "Tipo:", value = it.type)
            InfoRow(label = "Cantidad de empleados:", value = it.employees.toString())
            InfoRow(label = "Ubicación:", value = it.ubication)
            InfoRow(label = "Sitio web:", value = it.websiteUrl)

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Valores")
//            Text(text = it.values ?: "Sin información")

            Spacer(modifier = Modifier.height(16.dp))

            SectionTitle("Misión")
//            Text(text = it.mission ?: "Sin información")
        }
    } ?: Text("Cargando información...")
}


@Composable
fun RatingStars( // hacerlo componente ahorita
    rating: Float,
    modifier: Modifier = Modifier,
    starFilled: Painter,
    starHalf: Painter,
    starBorder: Painter
) {
    Row(modifier = modifier) {
        repeat(5) { index ->
            val starPainter = when {
                index + 1 <= rating -> starFilled
                index + 0.5 <= rating -> starHalf
                else -> starBorder
            }

            Icon(
                painter = starPainter,
                contentDescription = "Star Rating",
                modifier = Modifier.size(25.dp),
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun ReviewCard(review: CompanyReview) {

    Row {

        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("persona", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSecondaryContainer) // nombre de la persona
               // Text(text = "Hace ${daysAgo(review.createdAt)} días", style = MaterialTheme.typography.bodySmall)
            }

            RatingStars(
                rating = review.rating,
                starFilled = painterResource(id = R.drawable.icon_star_full),
                starBorder = painterResource(id = R.drawable.icon_star),
                starHalf = painterResource(id = R.drawable.icon_star_half)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = review.comment,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                color =  MaterialTheme.colorScheme.onPrimaryContainer   ,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun InfoRow(label: String, value: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.width(170.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color =  MaterialTheme.colorScheme.onPrimaryContainer   ,
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp),
        color =  MaterialTheme.colorScheme.onSecondaryContainer
    )
}

