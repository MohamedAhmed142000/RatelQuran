package com.example.ratelquran.presentation.surahdetails


import androidx.compose.foundation.layout.Arrangement
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
import com.example.ratelquran.domain.model.Ayah

@Composable
fun AyahItem(
    verses: List<Ayah>,
    fontFamily: FontFamily,
    modifier: Modifier = Modifier,
    surahNumber: Int,
) {
    val showBasmala = surahNumber != 9 && surahNumber != 1

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
            if (showBasmala) {
                Text(
                    text = "بِسْمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ",
                    fontSize = 28.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                val text = buildAnnotatedString {
                    verses.forEach { verse ->
                        append(verse.text)
                        append(" ")
                        append(verseNumberUnicode(verse.verse))
                        append(" ")
                    }
                }

                Text(
                    text = text,
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

fun verseNumberUnicode(number: Int): String {
    val start = 0x06DD
    val digits = number.toString().map {
        when (it) {
            '0' -> '٠'
            '1' -> '١'
            '2' -> '٢'
            '3' -> '٣'
            '4' -> '٤'
            '5' -> '٥'
            '6' -> '٦'
            '7' -> '٧'
            '8' -> '٨'
            '9' -> '٩'
            else -> it
        }
    }.joinToString("")

    return "﴿$digits﴾"
}