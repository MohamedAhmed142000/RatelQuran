package com.example.ratelquran.presentation.surahdetails


import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ratelquran.R
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.model.getSurahName
import com.example.ratelquran.domain.model.toVerse
import com.example.ratelquran.presentation.lastread.LastReadViewModel
import com.example.ratelquran.presentation.qurandetails.AyahItem
import com.example.ratelquran.presentation.tafsir.TafsirViewModel

enum class QuranDetailsType {
    SURAH, JUZ
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranDetailsScreen(
    mode: QuranDetailsType,
    surahNumber: Int? = null,
    surahNameArg: String? = null,
    juzNumber: JuzModel? = null,
    startAyah: Int, // This parameter is defined but not used. Consider if it's still needed.
    viewModel: QuranDetailsViewModel = hiltViewModel(),
    tafsirViewModel: TafsirViewModel = hiltViewModel(),
    lastReadViewModel: LastReadViewModel = hiltViewModel()
) {
    val amiriFont = FontFamily(Font(R.font.amiri_slanted))
    val quranFont = FontFamily(Font(R.font.me_quran))

    val versesState by viewModel.verses.collectAsState()
    val tafsirState by tafsirViewModel.tafsirState.collectAsState()

    var showTafsirDialog by remember { mutableStateOf(false) }
    var selectedAyahForTafsir by remember { mutableStateOf<Ayah?>(null) } // Renamed for clarity

    // The BottomSheet is now primarily handled within AyahItem.
    // This state might not be needed anymore, or only for specific cases not covered by AyahItem's sheet.
    // var showBottomSheet by remember { mutableStateOf(false) }
    // var selectedAyahForSheet by remember { mutableStateOf<Ayah?>(null) }

    LaunchedEffect(mode, surahNumber, juzNumber?.number) {
        when (mode) {
            QuranDetailsType.SURAH -> surahNumber?.let { viewModel.loadVerses(mode, it) }
            QuranDetailsType.JUZ -> juzNumber?.let {
                viewModel.loadVerses(
                    mode,
                    it
                )
            } // Pass juz number
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (mode) {
                            QuranDetailsType.SURAH -> "سورة ${
                                surahNameArg ?: getSurahName(
                                    surahNumber ?: 1
                                )
                            }"

                            QuranDetailsType.JUZ -> "الجزء ${juzNumber?.number ?: "غير محدد"}"
                        }, fontFamily = amiriFont
                    )
                })
        }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (versesState.isEmpty() && (mode == QuranDetailsType.JUZ && juzNumber != null || mode == QuranDetailsType.SURAH && surahNumber != null)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    when (mode) {
                        QuranDetailsType.JUZ -> {
                            if (juzNumber != null) {
                                val grouped =
                                    versesState.map { ayahFromState -> // ayahFromState is an Ayah
                                        ayahFromState.toVerse() // CORRECT: Call toVerse() without arguments
                                    }.groupBy { verse -> // verse is of type Verse
                                        verse.surahName
                                    }
                                Log.d("QuranDetailsScreen", "grouped: $grouped")
                                grouped.forEach { (suraName, surahVerses) ->
                                    Log.d(
                                        "QuranDetailsScreen",
                                        "suraName: $suraName, surahVerses: $surahVerses"
                                    )
                                    item {
                                        Text(
                                            text = "﴿ ســـورة $suraName ﴾",
                                            fontFamily = quranFont,
                                            style = MaterialTheme.typography.titleLarge,
                                            modifier = Modifier.fillMaxWidth(),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    item {
                                        AyahItem(
                                            verses = surahVerses.map { verse ->
                                                Ayah(
                                                    chapter = verse.surahNumber,
                                                    verse = verse.ayahNumber,
                                                    text = verse.text,
                                                    juzNumber = juzNumber.number
                                                )

                                            },
                                            fontFamily = quranFont,
                                            surahNumber = surahVerses.first().surahNumber,
                                            onAyahClick = { ayah ->
                                                // This is for simple tap, typically for selection or navigation
                                                // The old Tafsir loading on simple click is now handled by onTafsirClick from AyahItem's sheet
                                                lastReadViewModel.saveLastRead(
                                                    surahNumber = ayah.chapter,
                                                    ayahNumber = ayah.verse,
                                                    surahName = getSurahName(ayah.chapter)
                                                )
                                                // Decide if you still want to show tafsir on simple click,
                                                // or only through the bottom sheet option.
                                                // If only through bottom sheet, remove the next two lines:
                                                // selectedAyahForTafsir = ayah
                                                // tafsirViewModel.loadTafsir(ayah.chapter, ayah.verse)
                                                // showTafsirDialog = true
                                            },
                                            onListenClick = { ayah ->
                                                // Implement your listen logic here
                                                // e.g., play audio for the ayah
                                                println("Listen to Ayah: ${ayah.chapter}:${ayah.verse}")
                                                // You might want to show some UI indication or use a media player
                                            },
                                            onTafsirClick = { ayah ->
                                                selectedAyahForTafsir = ayah
                                                tafsirViewModel.loadTafsir(ayah.chapter, ayah.verse)
                                                showTafsirDialog = true
                                            },
                                            onBookmarkClick = { ayah ->
                                                lastReadViewModel.saveLastRead(
                                                    surahNumber = ayah.chapter,
                                                    ayahNumber = ayah.verse,
                                                    surahName = getSurahName(ayah.chapter)
                                                )
                                                // Optionally, show a snackbar or toast that it was bookmarked
                                                println("Bookmarked Ayah: ${ayah.chapter}:${ayah.verse}")
                                            })
                                    }
                                }
                            }
                        }

                        QuranDetailsType.SURAH -> {
                            // versesState for SURAH mode should already be List<Ayah>
                            // If it's not, you might need a mapping similar to JUZ mode's it.toVerse()
                            val ayahsForSurah = versesState.map {
                                // Assuming versesState contains Ayah objects or can be mapped to them
                                // If versesState is already List<Ayah> with correct Juz info (e.g., 0 or null if not relevant for surah view)
                                // then you might not need this specific mapping here.
                                // However, AyahItem expects List<Ayah>
                                it
                            }

                            item { // Surah Name Header (Optional if AyahItem handles it or if it's clear from TopAppBar)
                                Text(
                                    text = "﴿ ســـورة ${surahNameArg ?: getSurahName(surahNumber ?: 1)} ﴾",
                                    fontFamily = quranFont,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                            item {
                                AyahItem(
                                    verses = ayahsForSurah, // Pass the list of Ayah
                                    fontFamily = quranFont,
                                    surahNumber = surahNumber ?: 1,
                                    onAyahClick = { ayah ->
                                        lastReadViewModel.saveLastRead(
                                            surahNumber = ayah.chapter,
                                            ayahNumber = ayah.verse,
                                            surahName = getSurahName(ayah.chapter)
                                        )
                                        // Decide simple click action (see comment in JUZ block)
                                    },
                                    onListenClick = { ayah ->
                                        println("Listen to Ayah: ${ayah.chapter}:${ayah.verse}")
                                        // Implement listen logic

                                    },
                                    onTafsirClick = { ayah ->
                                        selectedAyahForTafsir = ayah
                                        tafsirViewModel.loadTafsir(ayah.chapter, ayah.verse)
                                        showTafsirDialog = true
                                    },
                                    onBookmarkClick = { ayah ->
                                        lastReadViewModel.saveLastRead(
                                            surahNumber = ayah.chapter,
                                            ayahNumber = ayah.verse,
                                            surahName = getSurahName(ayah.chapter)
                                        )
                                        println("Bookmarked Ayah: ${ayah.chapter}:${ayah.verse}")
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    // This BottomSheet is no longer needed if AyahItem handles its own sheet for these actions.
    // If you remove it, also remove showBottomSheet and selectedAyahForSheet states.
    /*
    if (showBottomSheet && selectedAyahForSheet != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            BottomSheetContent( // This BottomSheetContent might be redundant now
                onListenClick = {
                    // Logic for listen, now likely passed to AyahItem's onListenClick
                    showBottomSheet = false
                },
                onTafsirClick = {
                    selectedAyahForSheet?.let { ayah ->
                        selectedAyahForTafsir = ayah
                        tafsirViewModel.loadTafsir(ayah.chapter, ayah.verse)
                        showTafsirDialog = true
                    }
                    showBottomSheet = false
                },
                onBookmarkClick = {
                    selectedAyahForSheet?.let { ayah ->
                        lastReadViewModel.saveLastRead(
                            surahNumber = ayah.chapter,
                            ayahNumber = ayah.verse,
                            surahName = getSurahName(ayah.chapter)
                        )
                    }
                    showBottomSheet = false
                }
            )
        }
    }
    */

    // Tafsir Dialog remains, triggered by onTafsirClick from AyahItem
    if (showTafsirDialog && selectedAyahForTafsir != null) {
        AlertDialog(onDismissRequest = {
            showTafsirDialog = false
            tafsirViewModel.clearTafsir()
        }, confirmButton = {
            TextButton(onClick = {
                showTafsirDialog = false
                tafsirViewModel.clearTafsir()
            }) { Text("إغلاق", fontFamily = amiriFont) }
        }, title = { Text("تفسير الآية", fontFamily = amiriFont) }, text = {
            Text(
                text = tafsirState?.text ?: "لا يوجد تفسير متوفر.", fontFamily = amiriFont
            )
        })
    }
}

// This BottomSheetContent composable might be redundant if AyahItem's internal sheet is used.
// If AyahItem has its own UI for these options, you can remove this.
// If you want to keep this for other purposes, ensure its props are correct.
@Composable
fun BottomSheetContent(
    onListenClick: () -> Unit, onTafsirClick: () -> Unit, onBookmarkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SheetItem(iconRes = R.drawable.ic_listen, text = "Listen", onClick = onListenClick)
        SheetItem(iconRes = R.drawable.ic_tafsir, text = "Tafsir", onClick = onTafsirClick)
        SheetItem(iconRes = R.drawable.ic_bookmark, text = "Bookmark", onClick = onBookmarkClick)
    }
}

@Composable
fun SheetItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit
) { // Changed 'icon' to 'iconRes' for clarity
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(id = iconRes),
            contentDescription = text
        ) // Use text as contentDescription
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
