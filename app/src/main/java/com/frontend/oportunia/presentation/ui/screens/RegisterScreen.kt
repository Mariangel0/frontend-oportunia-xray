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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.DatePickerFieldToModal
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.RegisterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun RegisterScreen(
    paddingValues: PaddingValues,
    viewModel: RegisterViewModel = viewModel(),
    navController: NavController
) {
    val name by viewModel.name.collectAsState()
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val birthDate by viewModel.birthDate.collectAsState()
    val education by viewModel.education.collectAsState()
    val isWorking by viewModel.isWorking.collectAsState()
    val company by viewModel.company.collectAsState()
    val jobPosition by viewModel.jobPosition.collectAsState()
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val error by viewModel.error.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onImageSelected(uri)
    }

    val errorMessage = when (error) {
        "empty_fields" -> stringResource(R.string.error_empty_fields)
        "password_mismatch" -> stringResource(R.string.error_password_mismatch)
        else -> null
    }

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.register),
        onBackClick = {navController.navigateUp()}
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable { imagePickerLauncher.launch("image/*") }
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Imagen de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
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



            item {
                TitleSection(title = stringResource(R.string.personal_data))
            }

            item {
                LabeledTextField(
                    label = stringResource(R.string.name),
                    value = name,
                    placeholder = stringResource(R.string.name_placeholder),
                    onValueChange = { viewModel.name.value = it }
                )
            }

            item {
                LabeledTextField(
                    label = stringResource(R.string.email),
                    value = email,
                    placeholder = stringResource(R.string.email_placeholder),
                    onValueChange = { viewModel.email.value = it }
                )
            }

            item {
                LabeledTextField(
                    label = stringResource(R.string.password),
                    value = password,
                    isPassword = true,
                    placeholder = stringResource(R.string.password_placeholder),
                    onValueChange = { viewModel.password.value = it }
                )
            }

            item {
                LabeledTextField(
                    label = stringResource(R.string.confirm_password),
                    value = confirmPassword,
                    isPassword = true,
                    placeholder = stringResource(R.string.confirm_password_placeholder),
                    onValueChange = { viewModel.confirmPassword.value = it }
                )
            }

            item {
                Text(
                    stringResource(R.string.birth_date),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                DatePickerFieldToModal(

                    onDateSelected = { viewModel.birthDate.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                TitleSection(title = stringResource(R.string.laboral_data))
            }

            item {
                Text(
                    stringResource(R.string.education),
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            itemsIndexed(education) { index, value ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = value,
                        onValueChange = { viewModel.updateEducation(index, it) },
                        placeholder = { Text(stringResource(R.string.degree_placeholder)) },
                        modifier = Modifier
                            .fillMaxWidth(0.90f)
                            .padding(horizontal = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                    IconButton(
                        onClick = { viewModel.removeEducation(index) },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Eliminar título educativo"
                        )
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
                    IconButton(onClick = { viewModel.addEducation() }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar título educativo",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = stringResource(R.string.add_degree),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.have_job))
                    Switch(
                        checked = isWorking,
                        onCheckedChange = { viewModel.isWorking.value = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            if (isWorking) {
                item {
                    LabeledTextField(
                        label = stringResource(R.string.company),
                        value = company,
                        placeholder = stringResource(R.string.company_placeholder),
                        onValueChange = { viewModel.company.value = it }
                    )
                }
                item {
                    LabeledTextField(
                        label = stringResource(R.string.job_position),
                        value = jobPosition,
                        placeholder = stringResource(R.string.job_position_placeholder),
                        onValueChange = { viewModel.jobPosition.value = it }
                    )
                }
            }
            item {
                errorMessage?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
            item {
                Button(
                    onClick = { viewModel.register() },
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
                .padding(horizontal = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
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
