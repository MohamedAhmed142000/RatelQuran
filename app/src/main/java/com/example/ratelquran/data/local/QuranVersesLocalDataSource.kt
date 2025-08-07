package com.example.ratelquran.data.local


import android.content.Context
import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
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
    fun loadVersesForJuz(juzModel: JuzModel): List<Ayah> {
        val allVerses = loadAllVersesGroupedBySurah().values.flatten()

        val startSurah = getSurahNumberByName(juzModel.startSurahName)
        val endSurah = getSurahNumberByName(juzModel.endSurahName)

        val result = mutableListOf<Ayah>()
        var insideRange = false

        for (verse in allVerses) {
            if (verse.chapter == startSurah && verse.verse == juzModel.startAyah) {
                insideRange = true
            }

            if (insideRange) {
                result.add(verse)
            }

            if (verse.chapter == endSurah && verse.verse == juzModel.endAyah) {
                break
            }
        }

        return result
    }


}
fun getSurahNumberByName(surahName: String): Int {
    val surahMap = mapOf(
        "الفاتحة" to  1,
        "البقرة" to   2,
        "آل عمران" to 3,
        "النساء" to   4,
        "المائدة" to  5,
        "الأنعام" to   6,
        "الأعراف" to   7,
        "الأنفال" to   8,
        "التوبة" to   9,
        "يونس" to     10,
        "هود" to 11,
        "يوسف" to 12,
        "الرعد" to 13,
        "إبراهيم" to 14,
        "الحجر" to 15,
        "النحل" to 16,
        "الإسراء" to 17,
        "الكهف" to 18,
        "مريم" to 19,
        "طه" to 20,
        "الأنبياء" to 21,
        "الحج" to 22,
        "المؤمنون" to 23,
        "النور" to 24,
        "الفرقان" to 25,
        "الشعراء" to 26,
        "النمل" to 27,
        "القصص" to 28,
        "العنكبوت" to 29,
        "الروم" to 30,
        "لقمان" to 31,
        "السجدة" to 32,
        "الأحزاب" to 33,
        "سبإ" to 34,
        "فاطر" to 35,
        "يس" to 36,
        "الصافات" to 37,
        "ص" to 38,
        "الزمر" to 39,
        "غافر" to 40,
        "فصلت" to 41,
        "الشورى" to 42,
        "الزخرف" to 43,
        "الدخان" to 44,
        "الجاثية" to 45,
        "الأحقاف" to 46,
        "محمد" to 47,
        "الفتح" to 48,
        "الحجرات" to 49,
        "ق" to 50,
        "الذاريات" to 51,
        "الطور" to 52,
        "النجم" to 53,
        "القمر" to 54,
        "الرحمن" to 55,
        "الواقعة" to 56,
        "الحديد" to 57,
        "المجادلة" to 58,
        "الحشر" to 59,
        "الممتحنة" to 60,
        "الصف" to 61,
        "الجمعة" to 62,
        "المنافقون" to 63,
        "التغابن" to 64,
        "الطلاق" to 65,
        "التحريم" to 66,
        "الملك" to 67,
        "القلم" to 68,
        "الحاقة" to 69,
        "المعارج" to 70,
        "نوح" to 71,
        "الجن" to 72,
        "المزمل" to 73,
        "المدثر" to 74,
        "القيامة" to 75,
        "الإنسان" to 76,
        "المرسلات" to 77,
        "النبأ" to 78,
        "النازعات" to 79,
        "عبس" to 80,
        "التكوير" to 81,
        "الانفطار" to 82,
        "المطففين" to 83,
        "الانشقاق" to 84,
        "البروج" to 85,
        "الطارق" to 86,
        "الأعلى" to 87,
        "الغاشية" to 88,
        "الفجر" to 89,
        "البلد" to 90,
        "الشمس" to 91,
        "الليل" to 92,
        "الضحى" to 93,
        "الشرح" to 94,
        "التين" to 95,
        "العلق" to 96,
        "القدر" to 97,
        "البينة" to 98,
        "الزلزلة" to 99,
        "العاديات" to 100,
        "القارعة" to 101,
        "التكاثر" to 102,
        "العصر" to 103,
        "الهمزة" to 104,
        "الفيل" to 105,
        "قريش" to 106,
        "الماعون" to 107,
        "الكوثر" to 108,
        "الكافرون" to 109,
        "النصر" to 110,
        "المسد" to 111,
        "الإخلاص" to 112,
        "الفلق" to 113,
        "الناس" to 114
    )

    return surahMap[surahName.trim()] ?: -1
}