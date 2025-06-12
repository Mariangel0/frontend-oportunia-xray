package com.frontend.oportunia.presentation.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout
import com.frontend.oportunia.presentation.viewmodel.CompanyViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ManageCompaniesScreen(
    navController: NavController,
    paddingValues: PaddingValues,
    companyViewModel: CompanyViewModel
) {
    val formState by companyViewModel.formState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    if (formState.success) {
        LaunchedEffect(true) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Empresa creada exitosamente")
            }
            delay(1500)
            companyViewModel.resetForm()
            navController.navigateUp()
        }
    }

    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.add_company),
        onBackClick = { navController.navigateUp() }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .navigationBarsPadding()
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { TitleSection(title = stringResource(R.string.company)) }

                item {
                    LabeledTextField(
                        label = stringResource(R.string.name_company),
                        value = formState.name,
                        placeholder = stringResource(R.string.name_company_placeholder),
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(name = newValue) } }
                    )
                }

                item {
                    LabeledTextField(
                        label = stringResource(R.string.description_company),
                        value = formState.description,
                        placeholder = stringResource(R.string.description_company_placeholder),
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(description = newValue) } }
                    )
                }

                item {
                    DropdownSectorField(
                        label = "Sector",
                        options = listOf("Tecnología", "Educación", "Salud"),
                        selected = formState.type,
                        onSelected = { newValue -> companyViewModel.onFieldChange { it.copy(type = newValue) } }
                    )
                }

                item {
                    LabeledTextField(
                        label = stringResource(R.string.location_company),
                        value = formState.location,
                        placeholder = stringResource(R.string.location_company_placeholder),
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(location = newValue) } }
                    )
                }

                item {
                    LabeledTextField(
                        label = "Número de empleados",
                        value = formState.employees,
                        placeholder = "Ej. 150",
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(employees = newValue) } }
                    )
                }

                item {
                    LabeledTextField(
                        label = "Sitio web",
                        value = formState.websiteUrl,
                        placeholder = "https://ejemplo.com",
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(websiteUrl = newValue) } }
                    )
                }

                item {
                    LabeledTextField(
                        label = "Visión",
                        value = formState.vision,
                        placeholder = "Describe la visión de la empresa",
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(vision = newValue) } }
                    )
                }

                item {
                    LabeledTextField(
                        label = "Misión",
                        value = formState.mission,
                        placeholder = "Describe la misión de la empresa",
                        onValueChange = { newValue -> companyViewModel.onFieldChange { it.copy(mission = newValue) } }
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = { navController.navigateUp() },
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(stringResource(R.string.cancel_button))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = { companyViewModel.createCompany() },
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text(stringResource(R.string.add_company_button), color = Color.White)
                        }
                    }
                }

                formState.errorMessage?.let { error ->
                    item {
                        Text(
                            text = error,
                            color = Color.Red,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(100.dp)) }
            }
        }
    }
}

@Composable
fun TitleSection(title: String) {
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
fun LabeledTextField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    readOnly: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(text = label, fontSize = 16.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            readOnly = readOnly,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSectorField(
    label: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
        Text(label, fontSize = 16.sp)
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                placeholder = { Text(stringResource(R.string.field_company_placeholder)) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.secondary
                )
            )
            ExposedDropdownMenu(expanded, onDismissRequest = { expanded = false }) {
                options.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            onSelected(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
