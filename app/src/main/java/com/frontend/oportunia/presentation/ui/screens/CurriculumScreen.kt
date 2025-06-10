package com.frontend.oportunia.presentation.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.CurriculumViewModel
import com.frontend.oportunia.presentation.viewmodel.UploadState
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes

fun getFileName(uri: Uri, context: Context): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            return it.getString(nameIndex)
        }
    }
    return null
}

@Composable
fun CurriculumScreen(
    studentId: Long,
    navController: NavController,
    paddingValues: PaddingValues,
    curriculumViewModel: CurriculumViewModel
) {
    val context = LocalContext.current
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }
    val uploadState by curriculumViewModel.uploadState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }

    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        fileUri = uri
        fileName = uri?.let { getFileName(it, context) }
    }

    LaunchedEffect(Unit) {
        curriculumViewModel.uploadState.collect { uploadState ->
            when (uploadState) {
                is UploadState.Success -> {
                    showSuccessDialog = true
                    fileUri = null
                    fileName = null
                    curriculumViewModel.resetUploadState()
                }
                is UploadState.Error -> {
                    Toast.makeText(context, uploadState.message, Toast.LENGTH_SHORT).show()
                    curriculumViewModel.resetUploadState()
                }
                else -> Unit
            }
        }
    }

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = "Analizar currículum",
        onBackClick = { navController.navigateUp() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sube tu currículum para su análisis", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

            FormatCard()

            Text(
                "Nuestro sistema puede analizar tu currículum de dos formas:\n\n" +
                        "• ☁️ Análisis en la nube: extrae tus habilidades, educación y experiencia para guardarlas automáticamente en tu perfil.\n\n" +
                        "• 🤖 Análisis con IA: genera recomendaciones personalizadas para mejorar tu currículum.",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 16.sp
            )

            FileUploadBox(
                fileUri = fileUri,
                fileName = fileName,
                onFileSelected = { documentLauncher.launch("application/pdf") },
                onFileRemoved = {
                    fileUri = null
                    fileName = null
                }
            )

            Crossfade(targetState = uploadState is UploadState.Loading) { isLoading ->
                if (isLoading) {
                    CircularProgressIndicator(color = Color(0xFF7C4DFF))
                } else {
                    AnalysisButtons(
                        fileUri = fileUri,
                        onCloudAnalysisClick = {
                            if (fileUri != null) {
                                try {
                                    curriculumViewModel.uploadToTextract(fileUri!!, studentId)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error subiendo archivo", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Selecciona un archivo primero", Toast.LENGTH_SHORT).show()
                            }
                        }
                        ,
                        onIAAnalysisClick = {
                            if (fileUri != null) {
                                try {
                                    val bytes = context.contentResolver.openInputStream(fileUri!!)!!.readBytes()
                                    curriculumViewModel.analyzeCurriculumWithIA(bytes, studentId)
                                    navController.navigate(NavRoutes.CVAnalysis.ROUTE)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error leyendo archivo", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "Selecciona un archivo primero", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }

        if (showSuccessDialog) {
            SuccessDialog {
                showSuccessDialog = false
            }
        }
    }
}

@Composable
fun FormatCard() {
    val context = LocalContext.current
    val url = "https://oportunia-cv.s3.us-east-2.amazonaws.com/curriculums/FORMATO_CV_CD.pdf"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD1C4E9)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Upload,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(30.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Ver formato oficial",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Solo para análisis en la nube",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 13.sp
                )
            }
        }
    }
}


@Composable
fun FileUploadBox(
    fileUri: Uri?,
    fileName: String?,
    onFileSelected: () -> Unit,
    onFileRemoved: () -> Unit
) {
    if (fileUri == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .border(1.dp, Color(0xFFBDBDBD), RoundedCornerShape(8.dp))
                .clickable { onFileSelected() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Upload, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text("Cargar currículum", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Text("Usa un archivo .pdf", fontSize = 12.sp)
            }
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD0F8CE), RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(fileName ?: "Archivo no válido", color = Color.Black)
            IconButton(onClick = onFileRemoved) {
                Icon(Icons.Default.Close, contentDescription = "Eliminar archivo")
            }
        }
    }
}

@Composable
fun AnalysisButtons(
    fileUri: Uri?,
    onCloudAnalysisClick: () -> Unit,
    onIAAnalysisClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onCloudAnalysisClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Análisis en la nube", color = Color.White)
        }

        Button(
            onClick = onIAAnalysisClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Análisis con IA", color = Color.White)
        }
    }
}

@Composable
fun SuccessDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C4DFF),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Aceptar", fontWeight = FontWeight.SemiBold)
            }
        },
        title = {
            Text(
                text = "Análisis exitoso",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column {
                Text("Tu currículum fue analizado correctamente.")
                Text("Tus datos se han actualizado en tu perfil.")
            }
        },
        shape = RoundedCornerShape(20.dp),
        containerColor = Color(0xFFE8F5E9)
    )
}
