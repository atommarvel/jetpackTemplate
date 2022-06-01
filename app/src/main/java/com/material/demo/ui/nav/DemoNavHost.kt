package com.material.demo.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet
import com.material.demo.data.ColorItem
import com.material.demo.data.StaticData
import com.material.demo.ui.MainViewModel
import com.material.demo.ui.detail.DetailBody
import com.material.demo.ui.feata.FeatABody
import com.material.demo.ui.home.ColorListBody

@Suppress("LongMethod")
@ExperimentalMaterialNavigationApi
@Composable
// File called DemoNavHost but function called PresNavHost. Was that intentional to account for scaling to multiple navhosts or something?
fun PresNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeNav.route,
        modifier = modifier
    ) {
        /**
         * This kinda depends on what you want the project to reflect:
         * If the goal is for the template to be scalable to many more screens, it feels like each
         * of the content params for these composable and bottomSheet calls for the builder would
         * end up not being centralized in this PresNavHost composable in a project that has many
         * screens. Would love to hear your thoughts on that and if you agree how you would structure
         * doing such a thing.
         * I've taken a stab at this in other projects and would love to compare notes :)
         */
        composable(route = AppScreens.HomeNav.route) {
            ColorListBody(
                onItemClicked = {
                    /**
                     * There are multiple places (here and below at the FeatABody call) that need
                     * to both know how to correctly navigate to the detail screen. Maybe this logic
                     * should be centralized in some sort of navigation service/manager that exposes
                     * a function called something like `navToDetail(name: String)`?
                     */
                    navController.navigate("${AppScreens.Detail.route}${it.name}")
                },
                viewModel.colorList,
            )
        }
        composable(route = AppScreens.NavA.route) {
            FeatABody(
                onLaunchClicked = {
                    navController.navigate("${AppScreens.Detail.route}$it")
                }
            )
        }
        //  adb shell am start -a android.intent.action.VIEW -d "http://www.m3demo.com/details/red"
        bottomSheet(
            route = "${AppScreens.Detail.route}{name}",
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                }
            ),
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "{name}"
                    uriPattern = "https://www.m3demo.com/details/{name}"
                }
            )
        ) { entry ->
            // Send ColorItem, or default if one is not found with that name
            val nameEntry = entry.arguments?.getString("name") ?: "None Found"
            val notFound = ColorItem(
                "Color Not Found - $nameEntry",
                "https://placehold.co/64x64/9e9e9e/9e9e9e.png",
                "https://placehold.co/128x256/9e9e9e/9e9e9e.png"
            )
            val colorItem = StaticData.COLOR_MAP[nameEntry.lowercase()] ?: notFound
            DetailBody(
                colorItem = colorItem
            )
        }
    }
}
