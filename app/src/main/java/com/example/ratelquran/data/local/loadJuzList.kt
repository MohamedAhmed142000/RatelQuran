package com.example.ratelquran.data.local

import android.content.Context
import com.example.ratelquran.domain.model.JuzModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun loadJuzList(context: Context): List<JuzModel> {
    val json = JsonReader.readJsonFromAssets(context,"juz_list.json")
    val type = object : TypeToken<List<JuzModel>>() {}.type
    return Gson().fromJson(json, type)
}
