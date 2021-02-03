package com.keepseung.daumimagesearch.di

import com.keepseung.daumimagesearch.model.ImageInfoService
import com.keepseung.daumimagesearch.viewmodel.ImageListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(service:ImageInfoService)
    fun inject(viewModel: ImageListViewModel)
}