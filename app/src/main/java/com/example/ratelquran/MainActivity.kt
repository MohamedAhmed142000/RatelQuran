package com.example.ratelquran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratelquran.data.local.QuranVersesLocalDataSource
import com.example.ratelquran.presentation.quranlist.juzlist.JuzListViewModel
import com.example.ratelquran.presentation.nav.AppNavGraph
import com.example.ratelquran.ui.theme.RatelQuranTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RatelQuranTheme {
                val context = LocalContext.current

                val viewModel = hiltViewModel<JuzListViewModel>()
                val juzList by viewModel.juz.collectAsState()

                val versesLocalDataSource = remember { QuranVersesLocalDataSource(context) }
                val versesGrouped = remember { versesLocalDataSource.loadAllVersesGroupedBySurah() }
                val allVerses = versesGrouped.values.flatten()

                AppNavGraph(
                    juzList = juzList,
                    fullQuranVerses = allVerses
                )
            }
        }
    }
}

