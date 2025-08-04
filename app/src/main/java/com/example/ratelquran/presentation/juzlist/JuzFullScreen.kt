package com.example.ratelquran.presentation.juzlist

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratelquran.data.local.QuranVersesLocalDataSource
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.model.Verse
import com.example.ratelquran.domain.model.toVerse

@Composable
fun JuzFullScreen(
    context: Context,
    juz: JuzModel
) {
    val dataSource = remember { QuranVersesLocalDataSource(context) }
    val allVerses = remember {
        dataSource.loadAllVersesGroupedBySurah()
            .flatMap { it.value } // كل الآيات
            .map { it.toVerse() } // نحولها إلى Verse
    }

    val versesInJuz = remember(juz, allVerses) {
        val result = mutableListOf<Verse>()
        var insideRange = false

        for (verse in allVerses) {
            if (verse.surahName == juz.startSurahName && verse.ayahNumber == juz.startAyah) {
                insideRange = true
            }

            if (insideRange) {
                result.add(verse)
            }

            if (verse.surahName == juz.endSurahName && verse.ayahNumber == juz.endAyah) {
                break
            }
        }
        result
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                text = "الجزء ${juz.number}",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        items(versesInJuz) { verse ->
            Text(
                text = verse.text,
                fontSize = 20.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
