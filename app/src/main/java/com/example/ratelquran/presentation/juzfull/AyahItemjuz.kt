package com.example.ratelquran.presentation.juzfull

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratelquran.R
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.model.Verse
import com.example.ratelquran.presentation.nav.verseNumberUnicode

@Composable
fun AyahItemjuz(
    juz: JuzModel, fontFamily: FontFamily, verses: List<Verse>
) {
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            Text(
                text = "الجزء ${juz.number}",
                fontSize = 24.sp,
                modifier = Modifier.padding(12.dp),
                textAlign = TextAlign.Center
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val versesBySurah = verses.groupBy { it.surahName }

                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    versesBySurah.forEach { (surahName, surahVerses) ->

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "﴿ ســـورة  $surahName ﴾",
                                fontSize = 30.sp,
                                fontFamily = FontFamily(Font(R.font.me_quran)),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        if (surahName != "التوبة" && surahName != "الفاتحة") {
                            Text(
                                text = "بِسْمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ",
                                fontSize = 24.sp,
                                fontFamily = fontFamily,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        val annotatedText = buildAnnotatedString {
                            surahVerses.forEach { verse ->
                                append(verse.text)
                                append(" ")
                                append(verseNumberUnicode(verse.ayahNumber))
                                append(" ")
                            }
                        }

                        Text(
                            text = annotatedText,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 4.dp)
                                .wrapContentHeight(),
                            fontSize = 25.sp,
                            lineHeight = 50.sp,
                            fontFamily = FontFamily(Font(R.font.me_quran)),
                        )
                    }
                }
            }
        }
    }
}
