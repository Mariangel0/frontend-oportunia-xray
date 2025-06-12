package com.frontend.oportunia.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.oportunia.R

sealed class BottomNavItem(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val iconRes: Int
) {
    data object MenuScreen : BottomNavItem(
        NavRoutes.Menu.ROUTE,
        R.string.menu,
        R.drawable.icon_menu
    )

    data object AdminMenuScreen : BottomNavItem(
        NavRoutes.AdminMenu.ROUTE,
        R.string.menu,
        R.drawable.icon_menu
    )

    data object AdviceScreen : BottomNavItem(
        NavRoutes.AdviceList.ROUTE,
        R.string.advice,
        R.drawable.icon_advice
    )

    data object ProfileScreen : BottomNavItem(
        NavRoutes.Profile.ROUTE,
        R.string.profile,
        R.drawable.icon_perfil
    )

    companion object {
        /**
         * Ítems para usuarios normales
         */
        fun items() = listOf(MenuScreen, AdviceScreen, ProfileScreen)

        /**
         * Ítems para administradores
         */
        fun adminItems() = listOf(AdminMenuScreen, ProfileScreen)
    }
}
