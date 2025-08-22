package com.example.ratelquran.presentation.quranlist.surahlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratelquran.domain.model.SurahInfo
// في SurahListScreen.kt
// ... (imports)
import androidx.compose.foundation.layout.Box // لتركيز العناصر
import androidx.navigation.NavController
import com.example.ratelquran.presentation.lastread.LastReadBanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahListScreen(
    viewModel: SurahListViewModel = hiltViewModel(),
    onSurahClick: (SurahInfo) -> Unit
    ,navController: NavController
) {
    val surahsState by viewModel.surahs.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = surahsState,
                    key = { surah -> surah.number }
                ) { surah ->
                    SurahItem(surah = surah, onClick = { onSurahClick(surah) })
                }
            }
        }
    }



