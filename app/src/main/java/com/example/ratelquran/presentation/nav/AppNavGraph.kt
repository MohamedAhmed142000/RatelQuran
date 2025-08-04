package com.example.ratelquran.presentation.nav


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.presentation.juzlist.JuzFullScreen
import com.example.ratelquran.presentation.juzlist.JuzListScreen
import com.example.ratelquran.presentation.quranlist.QuranListScreen
import com.example.ratelquran.presentation.surahdetails.SurahDetailsScreen

@Composable
fun AppNavGraph(
    juzList: List<JuzModel>,
    fullQuranVerses: List<Ayah>
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.SurahList) {
        composable(NavRoutes.SurahList) {
            QuranListScreen(
                onSurahClick = { surah ->
                    navController.navigate("${NavRoutes.SurahDetails}/${surah.number}/${surah.name}") {
                        popUpTo(NavRoutes.SurahDetails) { inclusive = true }
                    }
                },navController=navController)
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
        composable("juz_list") {
            JuzListScreen(context = LocalContext.current,navController=navController)
        }
        composable("juz_full/{juzNumber}",
            arguments = listOf(navArgument("juzNumber") { type = NavType.IntType })
        ) { backStackEntry ->
            val juzNumber = backStackEntry.arguments?.getInt("juzNumber") ?: return@composable
            val juz = juzList.find { it.number == juzNumber } ?: return@composable

            JuzFullScreen(context = LocalContext.current, juz = juz)
        }

    }
}
