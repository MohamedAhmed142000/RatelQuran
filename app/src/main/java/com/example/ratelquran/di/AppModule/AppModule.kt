package com.example.ratelquran.di.AppModule

import android.content.Context
import com.example.ratelquran.data.local.LastReadSharedPrefs
import com.example.ratelquran.data.local.QuranLocalDataSource
import com.example.ratelquran.data.local.QuranVersesLocalDataSource
import com.example.ratelquran.data.local.loadJuzList
import com.example.ratelquran.data.repository.LastReadRepositoryImpl
import com.example.ratelquran.data.repository.QuranRepositoryImpl
import com.example.ratelquran.domain.repository.LastReadRepository
import com.example.ratelquran.domain.repository.QuranRepository
import com.example.ratelquran.domain.usecase.ClearLastReadUseCase
import com.example.ratelquran.domain.usecase.GetAllSurahsUseCase
import com.example.ratelquran.domain.usecase.GetJuzListUseCase
import com.example.ratelquran.domain.usecase.GetLastReadUseCase
import com.example.ratelquran.domain.usecase.GetTafsirUseCase
import com.example.ratelquran.domain.usecase.GetVersesByJuzUseCase
import com.example.ratelquran.domain.usecase.GetVersesBySurahUseCase
import com.example.ratelquran.domain.usecase.SaveLastReadUseCase
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
        verseLocal: QuranVersesLocalDataSource,
        juzList: loadJuzList
    ): QuranRepository {
        return QuranRepositoryImpl(localDataSource, verseLocal, juzList )
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

    @Provides
    @Singleton
    fun provideGetJuzListUseCase(
        repository: QuranRepository
    ): GetJuzListUseCase {
        return GetJuzListUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetVersesByJuzUseCase(
        repository: QuranRepository
    ): GetVersesByJuzUseCase {
        return GetVersesByJuzUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideLoadJuzList(
        @ApplicationContext context: Context
    ): loadJuzList {
        return loadJuzList(context)
    }
    @Provides
    @Singleton
    fun provideGetTafsirUseCase(repository: QuranRepository): GetTafsirUseCase {
        return GetTafsirUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLastReadSharedPrefs(@ApplicationContext context: Context): LastReadSharedPrefs {
        return LastReadSharedPrefs(context)
    }

    @Provides
    @Singleton
    fun provideLastReadRepository(
        local: LastReadSharedPrefs
    ): LastReadRepository {
        return LastReadRepositoryImpl(local)
    }

    @Provides
    fun provideSaveLastReadUseCase(repo: LastReadRepository): SaveLastReadUseCase {
        return SaveLastReadUseCase(repo)
    }

    @Provides
    fun provideGetLastReadUseCase(repo: LastReadRepository): GetLastReadUseCase {
        return GetLastReadUseCase(repo)
    }

    @Provides
    fun provideClearLastReadUseCase(repo: LastReadRepository): ClearLastReadUseCase {
        return ClearLastReadUseCase(repo)
    }
}
