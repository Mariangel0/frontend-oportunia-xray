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
    data object CurriculumScreen : BottomNavItem(
        NavRoutes.CurriculumScreen.ROUTE,
        R.string.curriculum,
        R.drawable.icon_perfil
    )


    companion object {
        /**
         * Returns a list of all bottom navigation items to be displayed in the navigation bar.
         */
        fun items() = listOf(MenuScreen, AdviceScreen, ProfileScreen)
    }
}