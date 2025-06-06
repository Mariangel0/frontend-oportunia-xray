package com.frontend.oportunia.presentation.ui.screens

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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



fun getFileName(uri: Uri, context: Context): String? {
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (it.moveToFirst()) {
            return it.getString(nameIndex) // Devuelve el nombre del archivo
        }
    }
    return null
}

@Composable
fun CurriculumScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    curriculumViewModel: CurriculumViewModel
) {
    val context = LocalContext.current
    var fileUri by remember { mutableStateOf<Uri?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }
    val uploadState by curriculumViewModel.uploadState.collectAsState()

    val documentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        fileUri = uri
        fileName = uri?.let { getFileName(it, context) }
    }

    LaunchedEffect(key1 = curriculumViewModel) {
        curriculumViewModel.uploadState.collect { uploadState ->
            when (uploadState) {
                is UploadState.Success -> {
                    Toast.makeText(context, "Currículum subido exitosamente", Toast.LENGTH_SHORT).show()
                    fileUri = null
                    fileName = null
                }
                is UploadState.Error -> {
                    Toast.makeText(context, uploadState.message, Toast.LENGTH_SHORT).show()
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
            Text("Sube tu currículum para su análisis", style = MaterialTheme.typography.titleMedium)

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

            AnalysisButtons(
                fileUri = fileUri,
                onCloudAnalysisClick = {
                    fileUri?.let { curriculumViewModel.uploadToTextract(it, studentId = 1L) }
                },
                onIAAnalysisClick = {
                    Toast.makeText(context, "Funcionalidad IA pendiente", Toast.LENGTH_SHORT).show()
                }
            )
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
                Icon(Icons.Default.Upload, contentDescription = null, tint = Color(0xFF7C4DFF))
                Text("Cargar currículum", color = Color(0xFF7C4DFF), fontWeight = FontWeight.Bold)
                Text("Usa un archivo .pdf o .docx", fontSize = 12.sp)
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
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Análisis en la nube", color = Color.White)
        }

        Button(
            onClick = onIAAnalysisClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C4DFF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Análisis con IA", color = Color.White)
        }
    }
}
