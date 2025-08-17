package com.frontend.oportunia.presentation.ui.components

import androidx.compose.runtime.Composable

@Composable
fun Header(
    type: HeaderType,
    title: String = "",
    username: String = "",
    onBackClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    when (type) {
        HeaderType.BACK -> HeaderWithBackButton(title, onBackClick)
        HeaderType.WELCOME -> HeaderWelcome(username,  onLogoutClick)
        HeaderType.NONE -> {}
    }
}
