package com.example.ratelquran.data.local


import android.content.Context
import com.example.ratelquran.domain.model.Ayah
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuranVersesLocalDataSource(private val context: Context) {

    fun loadAllVersesGroupedBySurah(): Map<String, List<Ayah>> {
        val json = JsonReader.readJsonFromAssets(context, "quran_verses.json")
        val type = object : TypeToken<Map<String, List<Ayah>>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun loadVersesForSurah(surahNumber: Int): List<Ayah> {
        val all = loadAllVersesGroupedBySurah()
        return all[surahNumber.toString()] ?: emptyList()
    }
}
