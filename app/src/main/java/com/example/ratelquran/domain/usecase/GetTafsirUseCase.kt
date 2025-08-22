package com.example.ratelquran.domain.usecase

import com.example.ratelquran.domain.model.Tafsir
import com.example.ratelquran.domain.repository.QuranRepository

class GetTafsirUseCase(
    private val repository: QuranRepository
) {
    suspend operator fun invoke(surahNumber: Int, ayahNumber: Int): Tafsir? {
        return repository.getTafsirForAyah(surahNumber, ayahNumber)
    }
}
