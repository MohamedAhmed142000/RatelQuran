package com.example.ratelquran.domain.usecase


import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.repository.QuranRepository
import javax.inject.Inject

class GetVersesBySurahUseCase @Inject constructor(
    private val repository: QuranRepository
) {
    operator fun invoke(surahNumber: Int): List<Ayah> {
        return repository.getVersesBySurah(surahNumber)
    }
}
