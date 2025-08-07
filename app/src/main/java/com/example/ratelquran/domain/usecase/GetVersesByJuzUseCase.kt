package com.example.ratelquran.domain.usecase

import com.example.ratelquran.domain.model.Ayah
import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.repository.QuranRepository
import javax.inject.Inject

class GetVersesByJuzUseCase @Inject constructor(
    private val repository: QuranRepository
) {
    operator fun invoke(juzModel: JuzModel): List<Ayah> {
        return repository.getVersesByJuz(juzModel)
    }
}
