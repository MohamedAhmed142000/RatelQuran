package com.example.ratelquran.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.presentation.quranlist.juzlist.JuzListScreen
import com.example.ratelquran.presentation.quranlist.QuranListScreen
import com.example.ratelquran.presentation.surahdetails.QuranDetailsScreen
import com.example.ratelquran.presentation.surahdetails.QuranDetailsType

@Composable
fun AppNavGraph(
    juzList: List<JuzModel>,
    fullQuranVerses: List<Ayah>
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.SurahList) {

        // قائمة السور
        composable(NavRoutes.SurahList) {
            QuranListScreen(
                onSurahClick = { surah ->
                    navController.navigate(
                        "details/surah/${surah.number}/${surah.name}/1"
                    )
                },
                navController = navController
            )
        }

        // قائمة الأجزاء
        composable("juz_list") {
            JuzListScreen(navController = navController)
        }

        // عرض تفاصيل سورة
        composable(
            route = "details/surah/{surahNumber}/{surahName}/{startAyah}",
            arguments = listOf(
                navArgument("surahNumber") { type = NavType.IntType },
                navArgument("surahName") { type = NavType.StringType },
                navArgument("startAyah") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
            val startAyah = backStackEntry.arguments?.getInt("startAyah") ?: 1

            QuranDetailsScreen(
                mode = QuranDetailsType.SURAH,
                surahNumber = surahNumber,
                surahNameArg = surahName, // pass of surah name argument
                 startAyah = startAyah // pass of startAyah argument
            )
        }

        // show juz details
        composable(
            route = "details/juz/{juzNumber}/{startAyah}",
            arguments = listOf(
                navArgument("juzNumber") { type = NavType.IntType },
                navArgument("startAyah") { type = NavType.IntType }

            )
        ) { backStackEntry ->
            val juzNumber = backStackEntry.arguments?.getInt("juzNumber") ?: return@composable
            val juz = juzList.find { it.number == juzNumber } ?: return@composable
            val startAyah = backStackEntry.arguments?.getInt("startAyah") ?: 1

            if (juzNumber == null) {
                // Handle the case where juzNumber is null
                return@composable
            }
            QuranDetailsScreen(
                mode = QuranDetailsType.JUZ,
                juzNumber = juz,
                startAyah =startAyah
            )
        }
//        composable(
//            route = "details/surah/{surahNumber}/{surahName}/{startAyah}",
//            arguments = listOf(
//                navArgument("surahNumber") { type = NavType.IntType },
//                navArgument("surahName") { type = NavType.StringType },
//                navArgument("startAyah") { type = NavType.IntType }
//            )
//        ) { backStackEntry ->
//            val surahNumber = backStackEntry.arguments?.getInt("surahNumber") ?: 1
//            val surahName = backStackEntry.arguments?.getString("surahName") ?: ""
//            val startAyah = backStackEntry.arguments?.getInt("startAyah") ?: 1
//
//            QuranDetailsScreen(
//                mode = QuranDetailsType.SURAH,
//                surahNumber = surahNumber,
//                surahNameArg = surahName,
//                startAyah = startAyah
//            )
//
//        }

    }
}
