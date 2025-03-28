package com.frontend.oportunia.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.oportunia.R
import com.frontend.oportunia.presentation.ui.components.HeaderType
import com.frontend.oportunia.presentation.ui.layout.MainLayout

@Composable
fun PerfilScren(navController: NavController, paddingValues: PaddingValues){
    MainLayout(
        paddingValues = paddingValues,
        headerType = HeaderType.BACK,
        title = stringResource(R.string.profile),
        onBackClick = {navController.navigateUp()}
    ) {

    }
}