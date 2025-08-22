package com.example.ratelquran.data.repository

import com.example.ratelquran.data.local.LastReadLocalDataSource
import com.example.ratelquran.domain.model.LastRead
import com.example.ratelquran.domain.repository.LastReadRepository

class LastReadRepositoryImpl(
    private val local: LastReadLocalDataSource
) : LastReadRepository {
    override suspend fun saveLastRead(lastRead: LastRead) = local.saveLastRead(lastRead)
    override suspend fun getLastRead(): LastRead? = local.getLastRead()
    override suspend fun clearLastRead() = local.clearLastRead()
}