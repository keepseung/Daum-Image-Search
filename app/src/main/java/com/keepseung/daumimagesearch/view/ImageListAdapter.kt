package com.keepseung.daumimagesearch.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.keepseung.daumimagesearch.R
import com.keepseung.daumimagesearch.databinding.ItemImageBinding
import com.keepseung.daumimagesearch.model.ImageInfo

class ImageListAdapter(private val imageInfoList: ArrayList<ImageInfo>) :
    RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>(), ImageClickListener {

    // 페이징 시에 깜빡임 현상을 제거하기 위함
    init {
        setHasStableIds(true)
    }

    fun addMoreImageList(newArrayList: List<ImageInfo>) {
        // 추가적인 이미지 데이터가 온 경우
        imageInfoList.addAll(newArrayList)
        notifyDataSetChanged()
    }

    fun addImageList(newArrayList: List<ImageInfo>) {
        // 첫 페이지 이미지 데이터가 온 경우
        imageInfoList.clear()
        imageInfoList.addAll(newArrayList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemImageBinding>(
            inflater,
            R.layout.item_image,
            parent,
            false
        )
        return ImageViewHolder(view)
    }

    // 페이징 시에 깜빡임 현상을 제거하기 위함
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = imageInfoList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.view.image = imageInfoList[position]
        holder.view.listener = this
    }

    class ImageViewHolder(var view: ItemImageBinding) : RecyclerView.ViewHolder(view.root)

    override fun onClick(v: View, imageInfo: ImageInfo) {
        // 이미지 클릭시 이미지 전체보기 프레그먼트로 이동한다.
        val action = ImageListFragmentDirections.actionDetail(imageInfo)
        Navigation.findNavController(v).navigate(action)
    }

    companion object {
        private const val TAG = "ImageListAdapter"
    }

}