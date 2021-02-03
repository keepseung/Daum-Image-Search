package com.keepseung.daumimagesearch.view

import android.view.View
import com.keepseung.daumimagesearch.model.ImageInfo

interface ImageClickListener {
    fun onClick(v: View,animal:ImageInfo)
}