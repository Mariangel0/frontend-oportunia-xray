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
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.example.oportunia.R

@Composable
fun PerfilScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    //Simulate dates waiting for setup logged user
    var firstName by remember { mutableStateOf("Alex") }
    var lastName by remember { mutableStateOf("Marin") }
    var description by remember { mutableStateOf("Software Engineering Student") }
    var premium by remember { mutableStateOf(true) }
    var linkedinUrl by remember { mutableStateOf("https://linkedin.com/in/student1") }
    var githubUrl by remember { mutableStateOf("https://github.com/student1") }
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

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
            Spacer(modifier = Modifier.height(16.dp))

            ProfileImageSection(profileImageUri) { profileImageUri = it }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "$firstName $lastName",
                style = MaterialTheme.typography.titleMedium
            )

            if (premium) {
                Text(
                    text = "★ Premium",
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "About",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            LinkedInAndGitHubIcons(
                linkedinUrl = linkedinUrl,
                githubUrl = githubUrl
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Editar Perfil", color = Color.White)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun LinkedInAndGitHubIcons(linkedinUrl: String, githubUrl: String) {
    val context = LocalContext.current
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        IconButton(onClick = { openUrl(context, linkedinUrl) }) {
            Icon(
                imageVector = Icons.Filled.AccountBox,
                contentDescription = "LinkedIn",
                tint = Color(0xFF0A66C2)
            )
        }
        IconButton(onClick = { openUrl(context, githubUrl) }) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "GitHub",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ProfileImageSection(imageUri: Uri?, onImageSelected: (Uri?) -> Unit = {}) {
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
