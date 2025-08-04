package com.example.ratelquran.domain.model

data class Quarter(
    val hizbNumber: Int,
    val quarterNumber: Int,
    val startSurahName: String,
    val startAyah: Int,
    val endSurahName: String,
    val endAyah: Int
)
