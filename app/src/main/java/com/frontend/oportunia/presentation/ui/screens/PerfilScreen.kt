
package com.frontend.oportunia.presentation.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.ProfileViewModel
import com.example.oportunia.R
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*


data class ProfileUiState(
    val firstName: String = "",
    val lastName: String = "",
    val description: String = "",
    val location: String = "",
    val birthDate: String = "",
    val profileImageUri: Uri? = null
)

@Composable
fun PerfilScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    profileViewModel: ProfileViewModel
) {
    val user by profileViewModel.currentUser.collectAsState()
    val student by profileViewModel.loggedStudent.collectAsState()

    var uiState by remember { mutableStateOf(ProfileUiState()) }
    var isEditing by remember { mutableStateOf(false) }
    var profileInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        profileViewModel.loadProfile()
    }

    LaunchedEffect(user, student) {
        val u = user
        val s = student
        if ( u != null && s != null) {
            uiState = uiState.copy(
                firstName = u.firstName,
                lastName = u.lastName,
                description = s.description ?: "",
                location = s.location ?: "",
                birthDate = s.bornDate ?: ""
            )
            profileInitialized = true
        }
    }

    val premium = student?.premium ?: false
    val age = ageCal(uiState.birthDate)

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.profile),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileImageSection(uiState.profileImageUri) {
                uiState = uiState.copy(profileImageUri = it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileNameSection(uiState, isEditing) { newState -> uiState = newState }

            if (!isEditing) {
                ProfileSummarySection(uiState, age)
                LinkedInAndGitHubIcons(
                    linkedinUrl = student?.linkedinUrl ?: "",
                    githubUrl = student?.githubUrl ?: ""
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            Button(
                onClick = {
                    if (isEditing) {
                        student?.let {
                            profileViewModel.updateStudent(
                                it.copy(
                                    id = it.id,                          // 🔑 Asegura que el ID no se pierda
                                    user = it.user,                      // 🔑 Necesario si el backend lo requiere
                                    userId = it.userId,
                                    description = uiState.description,
                                    location = uiState.location,
                                    bornDate = uiState.birthDate
                                )
                            )
                        }
                        user?.let {
                            profileViewModel.updateUser(it.copy(
                                firstName = uiState.firstName,
                                lastName = uiState.lastName
                            ))
                        }
                    }
                    isEditing = !isEditing
                },
                modifier = Modifier.fillMaxWidth().height(40.dp).width(200.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (isEditing) "Guardar" else "Editar",
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ProfileNameSection(
    uiState: ProfileUiState,
    isEditing: Boolean,
    onValueChange: (ProfileUiState) -> Unit
) {
    if (isEditing) {
        ProfileTextField("Nombre", uiState.firstName) {
            onValueChange(uiState.copy(firstName = it))
        }
        ProfileTextField("Apellido", uiState.lastName) {
            onValueChange(uiState.copy(lastName = it))
        }
        ProfileTextField("Descripcion", uiState.description) {
            onValueChange(uiState.copy(description = it))
        }
        ProfileTextField("Ciudad", uiState.location) {
            onValueChange(uiState.copy(location = it))
        }
    } else {
        Text(text = "${uiState.firstName} ${uiState.lastName}", style = MaterialTheme.typography.titleLarge)
        Text(
            text = uiState.description,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProfileTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = MaterialTheme.colorScheme.primary) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = MaterialTheme.colorScheme.primary
        ),
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true
    )
}

@Composable
fun ProfileSummarySection(uiState: ProfileUiState, age: Int?) {
    Spacer(modifier = Modifier.height(24.dp))
    Text("Sobre mí", style = MaterialTheme.typography.titleMedium)
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (age != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_birth),
                    contentDescription = "Edad",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("$age")
            }
            Spacer(modifier = Modifier.width(32.dp))
        }
        if (uiState.location.isNotBlank()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_location),
                    contentDescription = "Ubicación",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(uiState.location)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Composable
fun LinkedInAndGitHubIcons(linkedinUrl: String, githubUrl: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = { openUrl(context, linkedinUrl) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_linkedin),
                contentDescription = "LinkedIn",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        IconButton(
            onClick = {  openUrl(context, githubUrl) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_github),
                contentDescription = "GitHub",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ProfileImageSection(imageUri: Uri?, onImageSelected: (Uri?) -> Unit) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> onImageSelected(uri) }

    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { launcher.launch("image/*") }
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Foto de perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Icono cámara",
                tint = Color.White,
                modifier = Modifier
                    .size(72.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

fun openUrl(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}

fun ageCal(bornDate: String?): Int? {
    if (bornDate.isNullOrBlank()) return null

    return try {
        val isoFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val javaToStringFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)

        val parsedDate = try {
            LocalDate.parse(bornDate.trim(), isoFormatter)
        } catch (e: DateTimeParseException) {
            LocalDate.parse(bornDate.trim(), javaToStringFormatter)
        }

        Period.between(parsedDate, LocalDate.now()).years
    } catch (e: Exception) {
        null
    }
}