package com.example.ratelquran.presentation.quranlist.juzlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun JuzListScreen(
    viewModel: JuzListViewModel = hiltViewModel(), navController: NavController
) {
    val juzList by viewModel.juz.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(items = juzList, key = { juz -> juz.number }) { juz ->
            ElevatedCard(
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .clickable {
                        navController.navigate("details/juz/${juz.number}/${juz.startAyah}")
                    }) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp, vertical = 12.dp
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "الجزء ${getNameJuz(juz.number)}", fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            "Juz ${juz.number}",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Navigate to Juz",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


private fun getNameJuz(number: Int): String {
    val allJuz = listOf(
        "الأول",
        "الثاني",
        "الثالث",
        "الرابع",
        "الخامس",
        "السادس",
        "السابع",
        "الثامن",
        "التاسع",
        "العاشر",
        "الحادي عشر",
        "الثاني عشر",
        "الثالث عشر",
        "الرابع عشر",
        "الخامس عشر",
        "السادس عشر",
        "السابع عشر",
        "الثامن عشر",
        "التاسع عشر",
        "العشرون",
        "الحادي والعشرون",
        "الثاني والعشرون",
        "الثالث والعشرون",
        "الرابع والعشرون",
        "الخامس والعشرون",
        "السادس والعشرون",
        "السابع والعشرون",
        "الثامن والعشرون",
        "التاسع والعشرون",
        "الثلاثون"
    )
    return allJuz.getOrNull(number - 1) ?: ""


}