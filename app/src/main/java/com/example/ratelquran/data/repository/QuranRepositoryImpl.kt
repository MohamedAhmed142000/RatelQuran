package com.example.ratelquran.data.repository

import com.example.ratelquran.data.local.QuranLocalDataSource
import com.example.ratelquran.data.local.QuranVersesLocalDataSource
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.domain.repository.QuranRepository

class QuranRepositoryImpl(
    private val localDataSource: QuranLocalDataSource,
    private val verseLocal: QuranVersesLocalDataSource
) : QuranRepository {
    override fun getAllSurahs(): List<SurahInfo> {
        return localDataSource.loadSurahListFromAssets()
    }

    override fun getVersesBySurah(surahNumber: Int): List<Ayah> {
        return verseLocal.loadVersesForSurah(surahNumber)
    }
}