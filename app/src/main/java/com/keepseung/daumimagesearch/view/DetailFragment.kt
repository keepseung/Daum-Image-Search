package com.keepseung.daumimagesearch.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.keepseung.daumimagesearch.R
import com.keepseung.daumimagesearch.databinding.FragmentDetailBinding
import com.keepseung.daumimagesearch.model.ImageInfo
import com.keepseung.daumimagesearch.model.ImagePalette


class DetailFragment : Fragment() {

    var imageInfo: ImageInfo? = null
    private lateinit var dataBinding:FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리스트 Fragment에서 전달한 이미지 객체를 받는다.
        arguments?.let {
            imageInfo = DetailFragmentArgs.fromBundle(it).image
        }

        // 이미지 객체에서 가져온 URL을 통해 배경색을 가져온다.
        imageInfo?.imageUrl?.let{
            setupBackgroundColor(it)
        }

        // UI에 ImageInfo 객체를 바인딩한다.
        dataBinding.image = imageInfo


    }

    fun setupBackgroundColor(url:String) {
        // Palette 라이브러리를 사용해서 배경색을 가져온다.
        Glide.with(this).asBitmap().load(url).into(object :CustomTarget<Bitmap>(){
            override fun onLoadCleared(placeholder: Drawable?) {

            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                Palette.from(resource)
                        .generate(){
                            palette ->
                            val intColor = palette?.lightMutedSwatch?.rgb ?:0
                            // 배경색을 UI에 바인딩한다.
                            dataBinding.palette = ImagePalette(intColor)
                        }
            }

        })
    }

}