package com.keepseung.daumimagesearch.di

import com.keepseung.daumimagesearch.model.ImageInfoApi
import com.keepseung.daumimagesearch.model.ImageInfoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
open class ApiModule {

    private val BASE_URL = "https://dapi.kakao.com/"
    // 인스턴스 생성
    @Provides
    fun provideImageApi(): ImageInfoApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ImageInfoApi::class.java)
    }

    // ImageInfoService 인스턴스 생성
    @Provides
    open fun provideImageApiService():ImageInfoService{
        return ImageInfoService()
    }
}