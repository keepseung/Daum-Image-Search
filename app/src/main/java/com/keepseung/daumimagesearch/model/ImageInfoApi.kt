package com.keepseung.daumimagesearch.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageInfoApi {

    // 검색한 단어에 해당하는 이미지 리스트 가져오는 요청
    @GET("v2/search/image")
    fun getImageList(
        @Header("Authorization") adminKey:String,
        @Query("query") key: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<ImageResponse>
}