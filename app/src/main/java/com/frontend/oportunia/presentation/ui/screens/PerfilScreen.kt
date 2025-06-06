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
import androidx.compose.material.icons.filled.LocationOn
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
import com.frontend.oportunia.presentation.viewmodel.LoginViewModel
import com.example.oportunia.R
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun PerfilScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    loginViewModel: LoginViewModel
) {
//    val student by loginViewModel.loggedStudent.collectAsState()
//
//    val firstName = student?.user?.firstName ?: ""
//    val lastName = student?.user?.lastName ?: ""
//    val description = student?.description ?: ""
//    val premium = student?.premium ?: false
//    val linkedinUrl = student?.linkedinUrl ?: ""
//    val githubUrl = student?.githubUrl ?: ""
//    val age = ageCal(student?.bornDate ?: "")
//    val location = student?.location ?: ""
//    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.profile),
        onBackClick = { navController.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
//            Spacer(modifier = Modifier.height(16.dp))
//
//            ProfileImageSection(profileImageUri) { profileImageUri = it }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "$firstName $lastName",
//                style = MaterialTheme.typography.titleMedium
//            )
//
//            if (premium) {
//                Text(
//                    text = "★ Premium",
//                    color = Color(0xFFFFC107),
//                    style = MaterialTheme.typography.bodyMedium
//                )
//            }
//
//            Text(
//                text = description,
//                style = MaterialTheme.typography.bodyMedium,
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "Sobre mi",
//                style = MaterialTheme.typography.titleMedium,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            if (age != null || location.isNotBlank()) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 24.dp),
//                    horizontalArrangement = Arrangement.spacedBy(16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    if (age != null) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.icon_birth),
//                                contentDescription = "Edad",
//                                tint = MaterialTheme.colorScheme.primary,
//                                modifier = Modifier.size(20.dp)
//                            )
//                            Spacer(modifier = Modifier.width(6.dp))
//                            Text(
//                                text = "$age",
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.onBackground
//                            )
//                        }
//                    }
//
//                    if (location.isNotBlank()) {
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.weight(1f)
//                        ) {
//                            Icon(
//                                painter = painterResource(id = R.drawable.icon_location),
//                                contentDescription = "Ubicación",
//                                tint = MaterialTheme.colorScheme.primary,
//                                modifier = Modifier.size(20.dp)
//                            )
//                            Spacer(modifier = Modifier.width(6.dp))
//                            Text(
//                                text = location,
//                                style = MaterialTheme.typography.bodyMedium,
//                                color = MaterialTheme.colorScheme.onBackground
//                            )
//                        }
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(12.dp))
//            }
//
//            LinkedInAndGitHubIcons(
//                linkedinUrl = linkedinUrl,
//                githubUrl = githubUrl
//            )
//
//            Button(
//                onClick = {},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(48.dp),
//                shape = MaterialTheme.shapes.small,
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary
//                )
//            ) {
//                Text("Editar Perfil", color = Color.White)
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
        }
    }
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
            onClick = {},
           // onClick = { openUrl(context, linkedinUrl) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_linkedin),
                contentDescription = "LinkedIn",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
        }

        IconButton(
            onClick = {},
            //onClick = {  openUrl(context, githubUrl) },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_github),
                contentDescription = "GitHub",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
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

fun ageCal(bornDate: String): Int? {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fechaNacimiento = LocalDate.parse(bornDate.trim(), formatter)
        val hoy = LocalDate.now()
        Period.between(fechaNacimiento, hoy).years
    } catch (e: Exception) {
        null
    }
}
