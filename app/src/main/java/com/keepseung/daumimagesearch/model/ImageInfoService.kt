package com.keepseung.daumimagesearch.model

import com.keepseung.daumimagesearch.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class ImageInfoService {

    // 미리 생성한 객체를 Component에게 주입받기
    @Inject
    lateinit var api: ImageInfoApi

    companion object {
        private const val LIST_SIZE = 30
        private const val ADMIN_KEY = "KakaoAK 1574b16eae4fc9d4871d941fcc4b35de"
    }

    init {
        DaggerApiComponent.create().inject(this)
    }

    // 이미지 리스트 요청
    fun getImageList(query:String, page:Int): Single<ImageResponse>{
        return api.getImageList(ADMIN_KEY,query, page,LIST_SIZE)
    }
}