package com.frontend.oportunia.presentation.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.domain.model.User
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import com.frontend.oportunia.presentation.viewmodel.UserViewModel

@Composable
fun UserListScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel
) {
    val users by userViewModel.filteredUsers.collectAsState()
    val query by userViewModel.searchQuery.collectAsState()
    val error by userViewModel.errorMessage.collectAsState()

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.users),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { userViewModel.searchUsers(it) },
                placeholder = { Text("Buscar usuarios") },
                trailingIcon = {
                    IconButton(onClick = { userViewModel.searchUsers(query) }) {
                        Icon(Icons.Default.Search, contentDescription = "Buscar")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (error != null) {
                Text(error ?: "", color = MaterialTheme.colorScheme.error)
                Spacer(modifier = Modifier.height(8.dp))
            }

            LazyColumn {
                items(users) { user ->
                    UserItem(user = user, onDelete = { userViewModel.deleteUser(user) })
                    Divider()
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onDelete: () -> Unit) {
    var showDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Avatar",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text("${user.firstName} ${user.lastName}", style = MaterialTheme.typography.bodyLarge)
            Text(user.email ?: "sin correo", style = MaterialTheme.typography.bodySmall)
            Text("User", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        }

        IconButton(onClick = { showDialog = true }) {
            Icon(Icons.Default.Close, contentDescription = "Eliminar" , tint = MaterialTheme.colorScheme.primary)
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar elminacion", color = MaterialTheme.colorScheme.primary) },
            text = { Text("¿Está seguro que desea eliminar a ${user.firstName} ${user.lastName}?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDialog = false
                }) {
                    Text("Eliminar", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}