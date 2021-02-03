package com.keepseung.daumimagesearch.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.keepseung.daumimagesearch.R

// 이미지 로딩시 보여줄 프로그레스바 설정
fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

// 이미지를 화면에 로드하기 위한 ImageView 확장함수
fun ImageView.loadImage(uri:String?, progrssDrrawable: CircularProgressDrawable){
    val options = RequestOptions
            .placeholderOf(progrssDrrawable) // 로딩 중에 프로그래스바
            .error(R.mipmap.ic_launcher_round) // 에러 발생시
    Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(uri)
            .into(this)

}

// 이미지 뷰에서 사용할 url 로딩
@BindingAdapter("android:imageUrl")
fun loadImage(view:ImageView, url:String){
    view.loadImage(url, getProgressDrawable(view.context))
}

private const val TAG = "Util"


