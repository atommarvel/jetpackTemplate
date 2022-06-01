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
            when {
                route == HomeNav.route -> HomeNav
                route == NavA.route -> NavA
                /**
                 * Not the most elegant way to check... Maybe the AppScreen should have a regex matcher?
                 * Here's a starting point of where I've been playing around with representing screens with args in a project:
                 *     - https://github.com/atommarvel/shears-for-kutt/blob/main/app/src/main/java/com/radiantmood/kuttit/nav/destination/DestinationSpec.kt
                 *
                 * Note that the project as a whole is nowhere near best practices or production readiness.
                 * It's more of a testbed for playing around with things for me :)
                 */
                route.startsWith(Detail.route) -> Detail
                else -> {
                    Log.w("DemoApp", "The provided route '$route' is not known." )
                    null
                }
            }
    }
}
