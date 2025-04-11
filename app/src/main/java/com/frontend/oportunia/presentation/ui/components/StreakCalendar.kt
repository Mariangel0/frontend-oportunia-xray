package com.frontend.oportunia.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.oportunia.R
import com.frontend.oportunia.data.remote.dto.WeekDay
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun StreakCalendar() {
    // Get the current date
    val currentDate = LocalDate.now()

    // Calculate the start of the week (Monday)
    val startOfWeek = currentDate.minusDays(currentDate.dayOfWeek.value.toLong() - 1)

    // Generate week days
    val weekDays = (0..4).map { offset ->
        val date = startOfWeek.plusDays(offset.toLong())
        WeekDay(
            abbreviation = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(),
            number = date.dayOfMonth,
            isToday = date == currentDate
        )
    }

    // Usar el composable reutilizable
    CustomCardWithText(
        title = stringResource(id = R.string.streak)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp, top = 20.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weekDays.forEach { weekDay ->
                // Configurar colores, tamaños, y fondo basado en el día
                val textColor = if (weekDay.isToday) MaterialTheme.colorScheme.onPrimary else Color.Gray
                val numberColor = if (weekDay.isToday) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                val backgroundColor = if (weekDay.isToday) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.background

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(70.dp)
                            .background(
                                color = backgroundColor,
                                shape = MaterialTheme.shapes.large
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = weekDay.abbreviation,
                                color = textColor,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(bottom = 10.dp)
                            )
                            Text(
                                text = weekDay.number.toString(),
                                color = numberColor,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }
    }
}


