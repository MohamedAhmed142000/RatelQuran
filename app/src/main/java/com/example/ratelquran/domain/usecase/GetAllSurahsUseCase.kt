package com.example.ratelquran.domain.usecase

import com.example.ratelquran.domain.model.SurahInfo
import com.example.ratelquran.domain.repository.QuranRepository
import javax.inject.Inject


class GetAllSurahsUseCase@Inject constructor(private  val  repository: QuranRepository) {
    operator fun invoke():List<SurahInfo>{
        return repository.getAllSurahs()
    }
}