package com.example.ratelquran.data.local

import com.example.ratelquran.domain.model.LastRead

interface LastReadLocalDataSource {
    suspend fun saveLastRead(lastRead: LastRead)
    suspend fun getLastRead(): LastRead?
    suspend fun clearLastRead()
}