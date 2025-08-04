package com.example.ratelquran.presentation.quranlist


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.presentation.juzlist.JuzListScreen
import com.example.ratelquran.presentation.surahlist.SurahListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranListScreen(
    onSurahClick: (SurahInfo) -> Unit, navController: NavController
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("السور", "الأجزاء")
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        // ✅ Top App Bar
        TopAppBar(
            title = {
                Text("📖 رتــــــــــــــل", textAlign = TextAlign.Center)
            }
        )

        // ✅ Tabs
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        // ✅ Content
        when (selectedTab) {
            0 -> SurahListScreen(onSurahClick = onSurahClick)
            1 -> JuzListScreen(context = context, navController = navController)
        }
    }
}
