package com.example.ratelquran.domain.repository

import com.example.ratelquran.domain.model.LastRead

interface LastReadRepository {
    suspend fun saveLastRead(lastRead: LastRead)
    suspend fun getLastRead(): LastRead?
    suspend fun clearLastRead()
}