package com.devapps.clickdrama.ui.navigation

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Explore : Screen("explore")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("explore/{id}"){
        fun createRoute(id: Int) = "explore/$id"
    }
}