package com.example.ratelquran.presentation.quranlist.surahlist


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratelquran.R
import com.example.ratelquran.domain.model.SurahInfo

@OptIn(ExperimentalMaterial3Api::class) // قد تحتاجه لبعض مكونات Material 3
@Composable
fun SurahItem(
    surah: SurahInfo,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${surah.number}. سورة ${surah.name}",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily(Font(R.font.me_quran)),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (surah.revelationTypeAr == "مكية") "مكية" else "مدنية",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant // لون أفتح قليلاً للتمييز
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                         Text(text = "${surah.numberOfAyahs} آية", style = MaterialTheme.typography.bodySmall)
                    }
                }

                Text(
                    text = if (surah.revelationTypeAr == "مكية") "🕋" else "🕌",
                    fontSize = 26.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )

            }
        }
    }
}
