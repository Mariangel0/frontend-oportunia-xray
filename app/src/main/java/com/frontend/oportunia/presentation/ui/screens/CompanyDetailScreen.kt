package com.frontend.oportunia.presentation.ui.screens

import android.R.attr.thickness
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
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun CompanyDetailScreen (
    companyId: Long,
    companyViewModel: CompanyViewModel,
    navController: NavController,
    paddingValues: PaddingValues
){
    LaunchedEffect(companyId) {
        companyViewModel.loadCompanyById(companyId)
        companyViewModel.getReviewsForCompany(companyId)
    }

    val selectedCompany by companyViewModel.selectedCompany.collectAsState()
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val reviews by companyViewModel.companyReviewsList.collectAsState()
    val averageRating = if (reviews.isNotEmpty()) reviews.map { it.rating }.average().toFloat() else 0f

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = selectedCompany?.name ?: stringResource(id = R.string.reviews),
        onBackClick = {navController.navigateUp()}
    ) {
        Column(
            modifier = Modifier
            .padding(24.dp),
        ){
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "%.1f".format(averageRating), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${reviews.size} ${stringResource(id = R.string.reviews)}" ,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )

            }
            RatingStars(
                rating = averageRating,
                starFilled = painterResource(id = R.drawable.icon_star_full),
                starBorder = painterResource(id = R.drawable.icon_star),
                starHalf = painterResource(id = R.drawable.icon_star_half)
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )

        Spacer(modifier = Modifier.height(16.dp))


        reviews.forEach { review ->
            ReviewCard(review)
            Modifier.padding(vertical = 8.dp)
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
        }

    }
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
                Text(text = "Lorem ipsum", fontWeight = FontWeight.Bold) // nombre de la persona
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
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

fun daysAgo(date: Long): Int {
    val diff = System.currentTimeMillis() - date
    return (diff / (1000 * 60 * 60 * 24)).toInt()
}