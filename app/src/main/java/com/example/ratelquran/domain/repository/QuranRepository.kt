package com.example.ratelquran.domain.repository

import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.model.SurahInfo

interface QuranRepository {
    fun getAllSurahs():List<SurahInfo>
    fun getVersesBySurah(surahNumber: Int): List<Ayah>
    fun getAllJuz():List<JuzModel>
    fun getVersesByJuz(juzModel: JuzModel): List<Ayah> // ✅ جديد


}