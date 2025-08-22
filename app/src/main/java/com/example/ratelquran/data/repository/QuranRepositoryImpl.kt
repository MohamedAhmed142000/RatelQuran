package com.example.ratelquran.data.repository

import com.example.ratelquran.data.local.QuranLocalDataSource
import com.example.ratelquran.data.local.QuranVersesLocalDataSource
import com.example.ratelquran.data.local.loadJuzList
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.domain.model.Tafsir
import com.example.ratelquran.domain.repository.QuranRepository

class QuranRepositoryImpl(
    private val localDataSource: QuranLocalDataSource,
    private val verseLocal: QuranVersesLocalDataSource,
    private  val juzList: loadJuzList,
) : QuranRepository {
    override fun getAllSurahs(): List<SurahInfo> {
        return localDataSource.loadSurahListFromAssets()
    }

    override fun getVersesBySurah(surahNumber: Int): List<Ayah> {
        return verseLocal.loadVersesForSurah(surahNumber)
    }

    override fun getAllJuz(): List<JuzModel> {
        return juzList.loadJuzListload()
    }
    override fun getVersesByJuz(juzModel: JuzModel): List<Ayah> {
        return verseLocal.loadVersesForJuz(juzModel) // هنضيفها في خطوة قادمة
    }
    override suspend fun getTafsirForAyah(surahNumber: Int, ayahNumber: Int): Tafsir? {
        return verseLocal.getTafsirForAyah(surahNumber, ayahNumber)
    }

}