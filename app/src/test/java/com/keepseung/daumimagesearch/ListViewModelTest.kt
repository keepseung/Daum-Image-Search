package com.keepseung.daumimagesearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.keepseung.daumimagesearch.di.DaggerApiComponent
import com.keepseung.daumimagesearch.model.ImageInfo
import com.keepseung.daumimagesearch.model.ImageInfoService
import com.keepseung.daumimagesearch.model.ImageResponse
import com.keepseung.daumimagesearch.viewmodel.ImageListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var imageService:ImageInfoService

    var listViewModel = ImageListViewModel()
    val page = 1

    private val query = "test"

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        DaggerApiComponent.builder()
            .apiModule(ApiModuleTest(imageService))
            .build()
            .inject(listViewModel)
    }

    @Test
    fun getAnimalsSuccess(){
        val image = ImageInfo("2019-07-04T12:30:30+0530", "tistory"
            ,"https://search1.kakaocdn.net/argon/130x130_85_c/4u2l0OT2qjm","http://postfiles3.naver.net/20160526_226/dj14days_1464239357026VRaRv_JPEG/%C7%F44.jpg?type=w1")
        val testImagelist: ArrayList<ImageInfo> = ArrayList()
        testImagelist.add(image)
        val imageList = ImageResponse(testImagelist)


        val testSingle = Single.just(imageList)
        Mockito.`when`(imageService.getImageList(query, page)).thenReturn(testSingle)
        listViewModel.getSearchResult(query,page)


        Assert.assertEquals(1, listViewModel.imageList.value?.size)
        Assert.assertEquals(false,listViewModel.isLoadError.value)
        Assert.assertEquals(false, listViewModel.isLoading.value)
        Assert.assertEquals(false, listViewModel.isEmptyResult.value)
    }

    @Test
    fun getAnimalsFailure(){
        val testSingle = Single.error<ImageResponse>(Throwable())

        Mockito.`when`(imageService.getImageList(query, page)).thenReturn(testSingle)

        listViewModel.getSearchResult(query,page)

        Assert.assertEquals(null, listViewModel.imageList.value?.size)
        Assert.assertEquals(false,listViewModel.isLoadError.value)
        Assert.assertEquals(true, listViewModel.isLoading.value)
        Assert.assertEquals(false, listViewModel.isEmptyResult.value)

    }


    @Before
    fun setupRxScheduler(){
        val immediate = object : Scheduler(){
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler-> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler-> immediate }
    }
}