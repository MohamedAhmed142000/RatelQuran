package com.example.ratelquran.data.local

import android.content.Context
import com.example.ratelquran.domain.model.LastRead

class LastReadSharedPrefs(context: Context) : LastReadLocalDataSource {
    private val prefs = context.getSharedPreferences("last_read_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_SURAH_NUMBER = "surah_number"
        private const val KEY_AYAH_NUMBER = "ayah_number"
        private const val KEY_SURAH_NAME = "surah_name"
    }

    override suspend fun saveLastRead(lastRead: LastRead) {
        prefs.edit()
            .putInt(KEY_SURAH_NUMBER, lastRead.surahNumber)
            .putInt(KEY_AYAH_NUMBER, lastRead.ayahNumber)
            .putString(KEY_SURAH_NAME, lastRead.surahName)
            .apply()
    }

    override suspend fun getLastRead(): LastRead? {
        val surah = prefs.getInt(KEY_SURAH_NUMBER, -1).takeIf { it != -1 } ?: return null
        val ayah = prefs.getInt(KEY_AYAH_NUMBER, -1).takeIf { it != -1 } ?: return null
        val name = prefs.getString(KEY_SURAH_NAME, null) ?: return null
        return LastRead(surah, ayah, name)
    }

    override suspend fun clearLastRead() {
        prefs.edit().clear().apply()
    }
}