package com.example.ratelquran.presentation.nav

fun verseNumberUnicode(number: Int): String {
    val start = 0x06DD
    val digits = number.toString().map {
        when (it) {
            '0' -> '٠'
            '1' -> '١'
            '2' -> '٢'
            '3' -> '٣'
            '4' -> '٤'
            '5' -> '٥'
            '6' -> '٦'
            '7' -> '٧'
            '8' -> '٨'
            '9' -> '٩'
            else -> it
        }
    }.joinToString("")

    return "﴿$digits﴾"
}