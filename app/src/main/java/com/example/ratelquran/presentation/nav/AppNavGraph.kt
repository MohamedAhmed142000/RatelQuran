package com.example.ratelquran.presentation.nav


import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ratelquran.presentation.surahdetails.SurahDetailsScreen
import com.example.ratelquran.presentation.surahlist.SurahListScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.SurahList) {
        composable(NavRoutes.SurahList) {
            SurahListScreen(
                onSurahClick = { surah ->
                    navController.navigate("${NavRoutes.SurahDetails}/${surah.number}/${surah.name}")
                })
        }

        composable(
            route = "${NavRoutes.SurahDetails}/{surahNumber}/{surahName}",
            arguments = listOf(
                navArgument("surahNumber") { type = NavType.IntType },
                navArgument("surahName") { type = NavType.StringType })
        ) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""

            SurahDetailsScreen(
                surahNumber = surahNumber,
                surahName = surahName
            )
        }
    }
}
