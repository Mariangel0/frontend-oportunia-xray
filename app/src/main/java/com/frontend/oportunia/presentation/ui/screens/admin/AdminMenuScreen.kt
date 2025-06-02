package com.frontend.oportunia.presentation.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout

@Composable
fun AdminMenuScreen(
    paddingValues: PaddingValues,
   // loginViewModel: LoginViewModel
) {
  //  val admin by loginViewModel.loggedStudent.collectAsState()
    val username =  "Administrador"

    Column(

        modifier = Modifier.fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
        ) {
        MainLayout(
            paddingValues = paddingValues,
            headerType = HeaderType.WELCOME,
            username = username, //Falta mejorar el header
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ActionCard(
                icon = painterResource(id = R.drawable.icon_companies),
                title = stringResource(id = R.string.btn_admin_companies),
                description = stringResource(id = R.string.description_admin_companies),
                onClick = { /* Acción */ }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ActionCard(
                icon =  painterResource(id = R.drawable.icon_users),
                title = stringResource(id = R.string.btn_admin_users),
                description = stringResource(id = R.string.description_admin_users),
                onClick = { /* Acción */ }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text =  stringResource(id = R.string.activities),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatCard(title =  stringResource(id = R.string.card_title_companies), value = "1")
                StatCard(title = stringResource(id = R.string.card_title_users), value = "1")
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun ActionCard(icon: Painter, title: String, description: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier.fillMaxWidth()

    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp),
                )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Button(onClick = onClick,
                modifier = Modifier.padding(start = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    stringResource(id = R.string.btn_admin),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}
@Composable
fun StatCard(title: String, value: String) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.width(180.dp)
            .height(100.dp)
            .padding(8.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}
