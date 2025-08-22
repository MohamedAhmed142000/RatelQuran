package com.example.ratelquran.presentation.lastread


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ratelquran.presentation.lastread.LastReadViewModel

@Composable
fun LastReadBanner(
    navController: NavController, viewModel: LastReadViewModel = hiltViewModel()
) {
    val lastRead = viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadLastRead()
    }

    lastRead.value?.let { last ->
        Card(
            modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                    navController.navigate("surah/${last.surahNumber}/${last.ayahNumber}")
                }) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = "واصل القراءة من: ${last.surahName}")
                Text(text = "آية ${last.ayahNumber}")
            }
        }
    }
}