package com.keepseung.daumimagesearch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.keepseung.daumimagesearch.di.DaggerApiComponent
import com.keepseung.daumimagesearch.model.ImageInfo
import com.keepseung.daumimagesearch.model.ImageInfoService
import com.keepseung.daumimagesearch.model.ImageResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// sharedpreferen를 사용하기 위한 Context를 얻기 위해
// application을 가져온다.
class ImageListViewModel : ViewModel() {

    val imageList by lazy { MutableLiveData<List<ImageInfo>>() }
    val isLoadError by lazy { MutableLiveData<Boolean>() }
    val isLoading by lazy { MutableLiveData<Boolean>() }
    val isEmptyResult by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: ImageInfoService

    companion object {
        private const val TAG = "ListViewModel"
    }

    init {
        DaggerApiComponent.builder()
            .build()
            .inject(this)
    }

    fun getSearchResult(query:String, page:Int) {
        isLoading.value = true
        getImageList(query,page)
    }

    // 외부에서 직접 호출해서 접근하지 못하게한다.
    private fun getImageList(query:String, page:Int) {
        disposable.add(
            apiService.getImageList(query, page)// 다음 검색 api 호출함
                .subscribeOn(Schedulers.newThread())// 새로운 스레드에서 작업함
                .observeOn(AndroidSchedulers.mainThread()) // 결과 값을 메인 스레드에서 받을 수 있게 함
                .subscribeWith(object : DisposableSingleObserver<ImageResponse>() {
                    override fun onSuccess(imageResponse: ImageResponse) {

                        // 페이지가 1이고, 검색 결과가 없는 경우
                        if (imageResponse.imageList.size ==0 && page==1){
                            // 검색 결과가 없다는 데이터를 설정한다.
                            isEmptyResult.value =true

                            // 그 이외인 경우
                        }else{
                            // 이미지 데이터를 넣습니다.
                            imageList.value = imageResponse.imageList
                            isEmptyResult.value =false
                        }
                        isLoadError.value = false
                        isLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        isLoading.value = false
                        isEmptyResult.value =false
                        imageList.value = null
                        isLoadError.value = true
                    }

                }))

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }


}