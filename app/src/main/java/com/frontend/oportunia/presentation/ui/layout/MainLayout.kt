package com.frontend.oportunia.presentation.ui.layout

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frontend.oportunia.presentation.ui.components.Header
import com.frontend.oportunia.presentation.ui.components.HeaderType

@Composable
fun MainLayout(
    paddingValues: PaddingValues,
    headerType: HeaderType = HeaderType.NONE,
    title: String = "",
    username: String = "",
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},

    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Header(
                type = headerType,
                title = title,
                username = username,
                onBackClick = onBackClick,
                onLogoutClick = onLogoutClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            content()
        }
    }
}
