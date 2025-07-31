package com.example.ratelquran.data.local

import android.content.Context
import java.io.IOException

object JsonReader {
    fun readJsonFromAssets(context: Context, fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            ""
        }
    }
}