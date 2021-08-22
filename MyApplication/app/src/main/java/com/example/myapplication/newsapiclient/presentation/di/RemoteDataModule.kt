package com.example.myapplication.newsapiclient.presentation.di

import com.example.myapplication.newsapiclient.data.NewsAPIService
import com.example.myapplication.newsapiclient.data.dataSource.NewsRemoteDataSource
import com.example.myapplication.newsapiclient.data.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService):NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsAPIService)
    }
}
