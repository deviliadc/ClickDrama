package com.devapps.clickdrama

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devapps.clickdrama.ui.component.BottomNavigation
import com.devapps.clickdrama.ui.navigation.Screen
import com.devapps.clickdrama.ui.screen.about.AboutScreen
import com.devapps.clickdrama.ui.screen.detail.DetailScreen
import com.devapps.clickdrama.ui.screen.explore.ExploreScreen
import com.devapps.clickdrama.ui.screen.favorite.FavoriteScreen
import com.devapps.clickdrama.ui.screen.home.HomeScreen
import com.devapps.clickdrama.ui.theme.ClickDramaTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickDramaApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold (
        bottomBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.About.route) {
            BottomNavigation(navController)
            }
        }, modifier = Modifier
            .fillMaxSize()
    ) {
        innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)){
            composable(Screen.Home.route){
                HomeScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    navigateToAbout = {
                        navController.navigate(Screen.About.route)
                    }
                )
            }
            composable(Screen.About.route){
                AboutScreen(
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt("id") ?: -1
                DetailScreen(
                    id = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Explore.route) {
                ExploreScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    navigateToAbout = {
                        navController.navigate(Screen.About.route)
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { id ->
                        navController.navigate(Screen.Detail.createRoute(id))
                    },
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }

    }
}


@Preview
@Composable
fun ClickDramaAppPreview() {
    ClickDramaTheme {
        ClickDramaApp()
    }
}