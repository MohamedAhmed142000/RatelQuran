package com.example.ratelquran.domain.model

import com.example.ratelquran.presentation.surahdetails.getSurahName

data class Ayah(
    val chapter: Int,
    val verse: Int,
    val text: String
)
fun Ayah.toVerse(): Verse = Verse(
    surahName = getSurahName(this.chapter),
    surahNumber = this.chapter,
    ayahNumber = this.verse,
    text = this.text
)
