package com.example.ratelquran.presentation.surahlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.R


@Composable
fun SurahItem(
    surah: SurahInfo, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "سورة    " + surah.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily(Font(R.font.me_quran))
                )
                Spacer(modifier = Modifier.width(200.dp))
                val revelationIcon = if (surah.revelationTypeAr == "مكية") "🕋" else "🕌"
                Text(
                    text = revelationIcon, fontSize = 28.sp
                )

            }
        }
    }
}
