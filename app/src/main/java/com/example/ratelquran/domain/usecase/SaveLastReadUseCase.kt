package com.example.ratelquran.domain.usecase

import com.example.ratelquran.domain.model.LastRead
import com.example.ratelquran.domain.repository.LastReadRepository

class SaveLastReadUseCase(private val repo: LastReadRepository) {
    suspend operator fun invoke(lastRead: LastRead) = repo.saveLastRead(lastRead)
}

class GetLastReadUseCase(private val repo: LastReadRepository) {
    suspend operator fun invoke(): LastRead? = repo.getLastRead()
}

class ClearLastReadUseCase(private val repo: LastReadRepository) {
    suspend operator fun invoke() = repo.clearLastRead()
}