package com.example.ratelquran.domain.model


data class SurahInfo(
    val number: Int,
    val name: String,
    val englishName: String,
    val revelationTypeEn: String,
    val revelationTypeAr: String,
    val numberOfAyahs: Int,
    val starts_at_juz: Int,
    val ends_at_juz: Int
)