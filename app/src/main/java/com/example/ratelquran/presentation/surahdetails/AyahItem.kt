package com.example.ratelquran.presentation.qurandetails

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.presentation.surahdetails.verseNumberUnicode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AyahItem(
    verses: List<Ayah>,
    fontFamily: FontFamily,
    surahNumber: Int,
    onAyahClick: (Ayah) -> Unit,
    onListenClick: (Ayah) -> Unit,
    onTafsirClick: (Ayah) -> Unit,
    onBookmarkClick: (Ayah) -> Unit
) {
    val showBasmala = surahNumber != 9 && surahNumber != 1
    var selectedAyahIndex by remember { mutableStateOf<Int?>(null) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var textLayoutResultState by remember { mutableStateOf<TextLayoutResult?>(null) } // State to hold TextLayoutResult

    if (showBottomSheet && selectedAyahIndex != null) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            val ayah = verses[selectedAyahIndex!!]
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SheetItem(icon = Icons.Default.PlayArrow, text = "Listen") {
                    onListenClick(ayah)
                    showBottomSheet = false
                }
                SheetItem(
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    text = "Tafsir"
                ) { // Corrected Icon
                    onTafsirClick(ayah)
                    showBottomSheet = false
                }
                SheetItem(icon = Icons.Default.BookmarkBorder, text = "Bookmark") {
                    onBookmarkClick(ayah)
                    showBottomSheet = false
                }
            }
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showBasmala) {
                Text(
                    text = "بِسْمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ",
                    fontSize = 24.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                val annotatedText = buildAnnotatedString {
                    verses.forEachIndexed { index, ayah ->
                        val isSelected = selectedAyahIndex == index
                        pushStringAnnotation(tag = "ayah", annotation = index.toString())
                        withStyle(
                            SpanStyle(
                                background = if (isSelected) MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.3f
                                ) else Color.Transparent,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontFamily = fontFamily
                            )
                        ) {
                            append(ayah.text)
                            append(" ")
                            append(verseNumberUnicode(ayah.verse))
                            append(" ")
                        }
                        pop()
                    }
                }

                ClickableText(
                    text = annotatedText,
                    style = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 48.sp,
                        textAlign = TextAlign.Justify
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = { touchOffset -> // touchOffset is an Offset
                                    textLayoutResultState?.let {
                                        val characterOffset = it.getOffsetForPosition(touchOffset)
                                        annotatedText.getStringAnnotations(
                                            "ayah",
                                            characterOffset,
                                            characterOffset
                                        )
                                            .firstOrNull()?.item?.toIntOrNull()?.let { index ->
                                                selectedAyahIndex = index
                                                showBottomSheet = true
                                            }
                                    }
                                },
                                onTap = { touchOffset -> // touchOffset is an Offset
                                    textLayoutResultState?.let {
                                        val characterOffset = it.getOffsetForPosition(touchOffset)
                                        annotatedText.getStringAnnotations(
                                            "ayah",
                                            characterOffset,
                                            characterOffset
                                        )
                                            .firstOrNull()?.item?.toIntOrNull()?.let { index ->
                                                selectedAyahIndex = index
                                                onAyahClick(verses[index])
                                            }
                                    }
                                }
                            )
                        },
                    onTextLayout = { // Get the TextLayoutResult
                        textLayoutResultState = it
                    },
                    onClick = {
                        // This is still required by ClickableText.
                        // Since onTap is handled by pointerInput, you can leave this empty,
                        // or if you want to be super safe, also call your logic here,
                        // ensuring that textLayoutResultState is up-to-date.
                        // However, it's usually better to have one source of truth for the tap.
                    }
                )
            }
        }
    }
}

@Composable
fun SheetItem(icon: ImageVector, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text)
        Spacer(Modifier.width(12.dp))
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}
