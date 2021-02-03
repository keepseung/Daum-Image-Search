package com.keepseung.daumimagesearch.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// 카카오 API 한 개 이미지에 대한 응답값
data class ImageInfo(
    @SerializedName("datetime")
    val datetime: String?,

    @SerializedName("display_sitename")
    val siteName: String?,

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("image_url")
    val imageUrl: String?
): Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(datetime)
        parcel.writeString(siteName)
        parcel.writeString(thumbnailUrl)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageInfo> {
        override fun createFromParcel(parcel: Parcel): ImageInfo {
            return ImageInfo(parcel)
        }

        override fun newArray(size: Int): Array<ImageInfo?> {
            return arrayOfNulls(size)
        }
    }
}

// 카카오 API 이미지 리스트 응답값
data class ImageResponse(
    @SerializedName("documents")
    var imageList:ArrayList<ImageInfo>
)

// 상세보기 화면에서 지정할 색상
data class ImagePalette(var color: Int)


