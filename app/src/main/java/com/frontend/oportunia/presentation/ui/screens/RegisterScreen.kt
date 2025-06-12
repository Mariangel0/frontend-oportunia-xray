package com.frontend.oportunia.presentation.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.HeaderType

import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.ui.navigation.NavRoutes
import com.frontend.oportunia.presentation.viewmodel.RegisterViewModel
import androidx.navigation.NavController
import com.frontend.oportunia.presentation.ui.components.DatePickerFieldToModal
import com.frontend.oportunia.presentation.ui.screens.admin.LabeledTextField

@Composable
fun RegisterScreen(
    paddingValues: PaddingValues,
    viewModel: RegisterViewModel = viewModel(),
    navController: NavController
) {
    /* ───────────── FLOWS ───────────── */
    val name            by viewModel.name.collectAsState()
    val lastName        by viewModel.lastName.collectAsState()
    val email           by viewModel.email.collectAsState()
    val password        by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isWorking       by viewModel.isWorking.collectAsState()
    val company         by viewModel.company.collectAsState()
    val jobPosition     by viewModel.jobPosition.collectAsState()
    val selectedImage   by viewModel.selectedImageUri.collectAsState()
    val error           by viewModel.error.collectAsState()
    val githubUrl       by viewModel.githubUrl.collectAsState()
    val linkedinUrl     by viewModel.linkedinUrl.collectAsState()
    val showDialog      by viewModel.showErrorDialog.collectAsState()

    /* ───────────── NUEVAS LISTAS (SnapshotStateList) ───────────── */
    val educationList  = viewModel.educationList
    val experienceList = viewModel.experienceList

    /* ───────────── SELECTOR DE IMAGEN ───────────── */
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onImageSelected(uri)
    }

    /* ───────────── CONTENIDO ───────────── */
    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.register),
        onBackClick = {navController.navigateUp()}
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 72.dp)
        ) {

            /* ───────────── SECCIÓN: FOTO ───────────── */
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedImage != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImage),
                            contentDescription = "Foto seleccionada",
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .clickable { launcher.launch("image/*") },
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(140.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .clickable { launcher.launch("image/*") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Icono de usuario",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(72.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
            }

            /* ───────────── DATOS PERSONALES ───────────── */
            item { TitleSection(stringResource(R.string.personal_data)) }

            item {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    LabeledTextField(
                        label = stringResource(R.string.name),
                        value = name,
                        placeholder = stringResource(R.string.name_placeholder),
                        onValueChange = { viewModel.name.value = it }
                    )
                    LabeledTextField(
                        label = stringResource(R.string.lastname),
                        value = lastName,
                        placeholder = stringResource(R.string.lastname_placeholder),
                        onValueChange = { viewModel.lastName.value = it }
                    )
                    LabeledTextField(
                        label = stringResource(R.string.email),
                        value = email,
                        placeholder = stringResource(R.string.email_placeholder),
                        onValueChange = { viewModel.email.value = it }
                    )
                    LabeledTextField(
                        label = stringResource(R.string.password),
                        value = password,
                        placeholder = stringResource(R.string.password_placeholder),
                        onValueChange = { viewModel.password.value = it },
                        isPassword = true
                    )
                    LabeledTextField(
                        label = stringResource(R.string.confirm_password),
                        value = confirmPassword,
                        placeholder = stringResource(R.string.confirm_password_placeholder),
                        onValueChange = { viewModel.confirmPassword.value = it },
                        isPassword = true
                    )

                        Text(
                            stringResource(R.string.birth_date),
                            fontSize = 16.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )
                        DatePickerFieldToModal(

                            onDateSelected = { viewModel.birthDate.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        )


                }
            }

            /* ───────────── DATOS LABORALES ───────────── */
            item { TitleSection(stringResource(R.string.laboral_data)) }

            item {
                Column(Modifier.padding(horizontal = 16.dp)) {
                    LabeledTextField(
                        label = stringResource(R.string.github),
                        value = githubUrl,
                        placeholder = stringResource(R.string.github_placeholder),
                        onValueChange = { viewModel.githubUrl.value = it }
                    )
                    LabeledTextField(
                        label = stringResource(R.string.linkedin),
                        value = linkedinUrl,
                        placeholder = stringResource(R.string.linkedin_placeholder),
                        onValueChange = { viewModel.linkedinUrl.value = it }
                    )
                }
            }

            /* ───────────── EDUCACIÓN ───────────── */
            item { TitleSection("Educación") }

            itemsIndexed(
                items = educationList,
                key = { _, item -> item.id }
            ) { index, entry ->
                Column(Modifier.padding(horizontal = 16.dp)) {
                    LabeledTextField(
                        label = "Título",
                        value = entry.degree,
                        placeholder = "",
                        onValueChange = { viewModel.updateEducationEntry(index, "degree", it) }
                    )
                    LabeledTextField(
                        label = "Institución",
                        value = entry.institution,
                        placeholder = "",
                        onValueChange = { viewModel.updateEducationEntry(index, "institution", it) }
                    )
                    LabeledTextField(
                        label = "Año de graduación",
                        value = entry.graduationYear,
                        placeholder = "",
                        onValueChange = { viewModel.updateEducationEntry(index, "graduationYear", it) }
                    )
                    IconButton(onClick = { viewModel.removeEducationEntry(index) }) {
                        Icon(Icons.Default.Close, contentDescription = "Eliminar educación")
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.addEducationEntry() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar educación",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(text = "Agregar educación", color = MaterialTheme.colorScheme.primary)
                }
            }

            /* ───────────── EXPERIENCIA ───────────── */
            item { TitleSection("Experiencia laboral") }

            itemsIndexed(
                items = experienceList,
                key = { _, item -> item.id }
            ) { index, entry ->
                Column(Modifier.padding(horizontal = 16.dp)) {
                    LabeledTextField(
                        label = "Empresa",
                        value = entry.company,
                        placeholder = "",
                        onValueChange = { viewModel.updateExperienceEntry(index, "company", it) }
                    )
                    LabeledTextField(
                        label = "Rol",
                        value = entry.role,
                        placeholder = "",
                        onValueChange = { viewModel.updateExperienceEntry(index, "role", it) }
                    )
                    LabeledTextField(
                        label = "Línea de tiempo",
                        value = entry.timeline,
                        placeholder = "Ej: 2020 - 2023",
                        onValueChange = { viewModel.updateExperienceEntry(index, "timeline", it) }
                    )
                    IconButton(onClick = { viewModel.removeExperienceEntry(index) }) {
                        Icon(Icons.Default.Close, contentDescription = "Eliminar experiencia")
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { viewModel.addExperienceEntry() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar experiencia",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(text = "Agregar experiencia", color = MaterialTheme.colorScheme.primary)
                }
            }
//            item {
//                errorMessage?.let {
//                    Text(
//                        text = it,
//                        color = MaterialTheme.colorScheme.error,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp)
//                    )
//                }
//            }
            item {
                Button(
                    onClick = {
                        viewModel.registerStudent(
                            onSuccess = {
                                navController.navigate(NavRoutes.Login.ROUTE)
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.button_register),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
            }



        }
    }

    /* ───────────── DIÁLOGO DE ERROR ───────────── */
    if (showDialog && error != null) {
        AlertDialog(
            onDismissRequest = { viewModel.showErrorDialog.value = false },
            confirmButton = {
                TextButton(onClick = { viewModel.showErrorDialog.value = false }) {
                    Text("Aceptar")
                }
            },
            title = { Text("Error") },
            text  = { Text(resolveErrorMessage(error)) }
        )
    }
}

@Composable
private fun TitleSection(title: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )
        Spacer(Modifier.height(4.dp))
    }
}


@Composable
private fun LabeledTextField(
    label: String,
    value: String,
    placeholder: String,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(text = label, fontSize = 16.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            visualTransformation = if (!isPassword) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

fun resolveErrorMessage(errorCode: String?): String {
    return when (errorCode) {
        "empty_fields"     -> "Por favor completa todos los campos obligatorios."
        "password_mismatch"-> "Las contraseñas no coinciden."
        "invalid_email"    -> "El correo electrónico no es válido."
        "register_failed"  -> "No se pudo registrar. Intenta más tarde."
        "network_error"    -> "Error de conexión. Verifica tu red."
        else               -> "Ocurrió un error inesperado."
    }
}
