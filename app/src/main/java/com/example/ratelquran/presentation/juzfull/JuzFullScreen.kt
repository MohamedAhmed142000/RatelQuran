package com.example.ratelquran.presentation.juzfull

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratelquran.R
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.model.toVerse

@Composable
fun JuzFullScreen(
    viewModel: JuzFullViewModel = hiltViewModel(),
    juz: JuzModel,
) {
    val font = FontFamily(Font(R.font.amiri_slanted))
    val verses by viewModel.verses.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadVerses(juz)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            AyahItemjuz(
                juz = juz,
                fontFamily = font,
                verses =  verses.map { it.toVerse() }
            )
        }
    }
}

