package com.keepseung.daumimagesearch.view

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewScrollListener(
    val func: () -> Unit,
    val layoutManager: GridLayoutManager
) : RecyclerView.OnScrollListener() {
    /**
     *  매개변수로 온 람다식 함수를(requestMoreImage) 호출함으로써 다음 페이지를 요청한다.
     *  다음 페이지 호출은 리스트 끝에 도달했을 때 호출한다.
     *  */
    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)
            ) {
                // 끝에 도달 했을 때
                func() // 매개변수로 넘겨받은 람다식 함수
                loading = true
            }
        }
    }

    open fun searchNewWord() {
        previousTotal = 0
    }

    companion object {
        private const val TAG = "RecyclerViewScrollListe"
    }
}