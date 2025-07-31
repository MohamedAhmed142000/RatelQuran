package com.example.ratelquran.data.local

import android.content.Context
import com.example.ratelquran.domain.model.SurahInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class QuranLocalDataSource(private val context: Context) {

    fun loadSurahListFromAssets(): List<SurahInfo> {
        val jsonString = JsonReader.readJsonFromAssets(context, "surah_list.json")
        val type = object : TypeToken<List<SurahInfo>>() {}.type
        return Gson().fromJson(jsonString, type) ?: emptyList()
    }
}