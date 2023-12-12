package com.devapps.clickdrama.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.GridView
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.devapps.clickdrama.R
import com.devapps.clickdrama.ui.navigation.NavigationItem
import com.devapps.clickdrama.ui.navigation.Screen
import com.devapps.clickdrama.ui.theme.ClickDramaTheme

@Composable
fun BottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val bottomNavigation = listOf(
            NavigationItem(
                title = stringResource(R.string.home),
                icon = Icons.Rounded.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.explore),
                icon = Icons.Rounded.GridView,
                screen = Screen.Explore
            ),
            NavigationItem(
                title = stringResource(R.string.favorite),
                icon = Icons.Rounded.Favorite,
                screen = Screen.Favorite
            )
        )
        bottomNavigation.map { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title)
               },
                label = {
                    Text(item.title)
                },
                selected = currentRoute == item.screen.route,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                    selectedTextColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    ClickDramaTheme {
        val navController = rememberNavController()
        BottomNavigation(navController = navController)
    }
}
