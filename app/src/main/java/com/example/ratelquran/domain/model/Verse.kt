package com.example.ratelquran.domain.model

data class Verse(
    val surahName: String,
    val surahNumber: Int,
    val ayahNumber: Int,
    val text: String
)
