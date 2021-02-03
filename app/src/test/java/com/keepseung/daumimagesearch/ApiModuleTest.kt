package com.keepseung.daumimagesearch

import com.keepseung.daumimagesearch.di.ApiModule
import com.keepseung.daumimagesearch.model.ImageInfoService

class ApiModuleTest(val mockService: ImageInfoService): ApiModule() {
    override fun provideImageApiService(): ImageInfoService {
        return mockService
    }
}