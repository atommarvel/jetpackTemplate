package com.material.demo.ui.nav

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.material.demo.R

sealed class AppScreens(val route: String, @StringRes val resourceId: Int, val icon: ImageVector) {
    object HomeNav : AppScreens("list", R.string.nav_home, Icons.Outlined.ListAlt)
    object NavA : AppScreens("nava", R.string.nav_nava, Icons.Outlined.Bookmarks)
    object Detail : AppScreens("details", R.string.nav_detail, Icons.Filled.Details)

    companion object {
        /**
         * @return a [AppScreens] that has a [AppScreens.route] that matches the passed in [route].
         */
        operator fun invoke(route: String): AppScreens? =
            when(route) {
                HomeNav.route -> HomeNav
                NavA.route -> NavA
                Detail.route -> Detail
                else -> {
                    Log.w("DemoApp", "The provided route '$route' is not known." )
                    null
                }
            }
    }
}
