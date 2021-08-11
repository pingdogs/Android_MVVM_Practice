package com.example.myapplication.newsapiclient.presentation.di

import com.example.myapplication.newsapiclient.domain.repository.NewsRepository
import com.example.myapplication.newsapiclient.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideNewsHeadLinesUseCase(newsRepository: NewsRepository): GetNewsHeadlinesUseCase{
        return GetNewsHeadlinesUseCase(newsRepository)
    }
    @Provides
    @Singleton
    fun provideSearchedNewsHeadLinesUseCase(newsRepository: NewsRepository): GetSearchedNewsHeadlinesUseCase{
        return GetSearchedNewsHeadlinesUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun provideSavedNewsUseCase(newsRepository: NewsRepository): SavedNewsUseCase{
        return SavedNewsUseCase(newsRepository)
    }
    @Provides
    @Singleton
    fun providegetSavedNewsUseCase(newsRepository: NewsRepository): GetSavedNewsUseCase{
        return GetSavedNewsUseCase(newsRepository)
    }

    @Provides
    @Singleton
    fun providegetDeleteSavedNewsUseCase(newsRepository: NewsRepository): DeleteSavedNewsUseCase{
        return DeleteSavedNewsUseCase(newsRepository)
    }
}