package com.example.ratelquran.domain.usecase

import com.example.ratelquran.domain.model.JuzModel
import com.example.ratelquran.domain.repository.QuranRepository
import jakarta.inject.Inject

class GetJuzListUseCase @Inject constructor(private val repository: QuranRepository) {
    operator fun invoke():List<JuzModel>{
        return repository.getAllJuz()
    }
}