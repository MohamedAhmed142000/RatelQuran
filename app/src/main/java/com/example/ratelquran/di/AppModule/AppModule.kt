package com.example.ratelquran.di.AppModule

import android.content.Context
import com.example.ratelquran.data.local.QuranLocalDataSource
import com.example.ratelquran.data.local.QuranVersesLocalDataSource
import com.example.ratelquran.data.repository.QuranRepositoryImpl
import com.example.ratelquran.domain.repository.QuranRepository
import com.example.ratelquran.domain.usecase.GetAllSurahsUseCase
import com.example.ratelquran.domain.usecase.GetVersesBySurahUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQuranLocalDataSource(
        @ApplicationContext context: Context
    ): QuranLocalDataSource {
        return QuranLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideQuranRepository(
        localDataSource: QuranLocalDataSource,
        verseLocal:QuranVersesLocalDataSource
    ): QuranRepository {
        return QuranRepositoryImpl(localDataSource, verseLocal )
    }

    @Provides
    @Singleton
    fun provideGetAllSurahsUseCase(
        repository: QuranRepository
    ): GetAllSurahsUseCase {
        return GetAllSurahsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideQuranVersesLocalDataSource(
        @ApplicationContext context: Context
    ): QuranVersesLocalDataSource {
        return QuranVersesLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun provideGetVersesBySurahUseCase(
        repository: QuranRepository
    ): GetVersesBySurahUseCase {
        return GetVersesBySurahUseCase(repository)
    }

}
