package com.example.myapplication.newsapiclient.presentation.di

import com.example.myapplication.BuildConfig
import com.example.myapplication.newsapiclient.data.NewsAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
    @Singleton
    @Provides
    fun provideNewsApiService(retrofit:Retrofit): NewsAPIService{
        return retrofit.create(NewsAPIService::class.java)
    }
}