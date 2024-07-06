package com.stahlt.cycleconfigurator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.stahlt.cycleconfigurator.ui.cycle.details.CycleDetailsScreen
import com.stahlt.cycleconfigurator.ui.cycle.form.CycleFormScreen
import com.stahlt.cycleconfigurator.ui.cycle.list.CycleListScreen

object Screen {
    const val List = "list"
    const val Form = "form"
    const val Details = "details"
}

@Composable
fun AppCycleConfigurator(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List,
        modifier = modifier
    ) {
        composable(route = Screen.List) {
            CycleListScreen(
                onAddPressed = { navController.navigate(Screen.Form) },
                onCyclePressed = { cycle-> navController.navigate("${Screen.Details}/${cycle.id}") }
            )
        }
        composable(
            route = "${Screen.Form}?id={id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType;
                nullable = true
            })
        ) {
            CycleFormScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
        composable(
            route = "${Screen.Details}/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
        ) {
            CycleDetailsScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }

}