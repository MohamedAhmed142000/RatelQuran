package com.example.ratelquran.domain.model


data class JuzModel(
    val number: Int,
    val startSurahName: String,
    val startAyah: Int,
    val endSurahName: String,
    val endAyah: Int
)