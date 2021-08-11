package com.example.myapplication.newsapiclient.presentation.di

import com.example.myapplication.newsapiclient.data.dataSource.NewsLocalDataSource
import com.example.myapplication.newsapiclient.data.dataSource.NewsRemoteDataSource
import com.example.myapplication.newsapiclient.domain.repository.NewsRepository
import com.example.myapplication.newsapiclient.domain.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource, newsLocalDataSource: NewsLocalDataSource):NewsRepository{
        return NewsRepositoryImpl(newsRemoteDataSource, newsLocalDataSource)
    }
}