package com.stahlt.cycleconfigurator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
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
    const val LIST = "list"
    const val FORM = "form"
    const val DETAILS = "details"
}

@Composable
fun AppCycleConfigurator(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.LIST,
        modifier = modifier
    ) {
        composable(route = Screen.LIST) {
            CycleListScreen(
                onAddPressed = { navController.navigate(Screen.FORM) },
                onCyclePressed = { cycle-> navController.navigate("${Screen.FORM}?id=${cycle.id}") }
            )
        }
        composable(
            route = "${Screen.FORM}?id={id}",
            arguments = listOf(navArgument(name = "id") {
                type = NavType.StringType
                nullable = true
            })
        ) {
            CycleFormScreen(
                onBackPressed = { navController.popBackStack() },
                onCycleSaved = { navigateToListCycles(navController) },
                onCycleDeleted = { navigateToListCycles(navController) }
            )
        }
        composable(
            route = "${Screen.DETAILS}/{id}",
            arguments = listOf(navArgument(name = "id") { type = NavType.IntType })
        ) {
            CycleDetailsScreen(
                onBackPressed = { navController.popBackStack() }
            )
        }
    }

}

private fun navigateToListCycles(navController: NavHostController) {
    navController.navigate(route = Screen.LIST) {
        popUpTo(navController.graph.findStartDestination().id) {
            inclusive = true
        }
    }
}
