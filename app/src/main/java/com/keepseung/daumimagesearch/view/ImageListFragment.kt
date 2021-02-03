package com.keepseung.daumimagesearch.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.keepseung.daumimagesearch.R
import com.keepseung.daumimagesearch.model.ImageInfo
import com.keepseung.daumimagesearch.viewmodel.ImageListViewModel
import kotlinx.android.synthetic.main.fragment_image_list.*
import java.util.*


class ImageListFragment : Fragment() {

    private lateinit var viewModel: ImageListViewModel
    private val listAdapter = ImageListAdapter(arrayListOf())
    private lateinit var scrollListener: RecyclerViewScrollListener
    private lateinit var query: String
    private var page =1

    private val imageListDataObserver = Observer<List<ImageInfo>>{ list->
        // 리스트의 데이터가 변경되었을 경우
        list?.let {
            // 리스트에 데이터를 넣어준다.
            imageRecyclerView.visibility = View.VISIBLE
            if (page==1) listAdapter.addImageList(it) else listAdapter.addMoreImageList(it)
        }

    }
    private val loadingLiveDataObserver = Observer<Boolean>{isLoading->
        // 페이지가 1인 경우(새로운 단어로 검색하는 경우)에만 로딩 프로그레스 바를 보여준다.
        if (page==1){
            loadingView.visibility =  if (isLoading) View.VISIBLE else View.GONE
            if (isLoading){
                listErrorTextView.visibility = View.GONE
                imageRecyclerView.visibility = View.GONE
            }
        }
    }

    private val errorLiveDataObserver = Observer<Boolean>{isError->
        // 데이터를 가져오다 에러가 발생한 경우
        // 에러 메세지 보여준다.
       listErrorTextView.visibility = if (isError) View.VISIBLE else View.GONE
    }
    private val emptyResultLiveDataObserver = Observer<Boolean>{isEmptyResult->
        // 검색 결과가 없는 경우
        // 검색 결과 없다는 메세지를 보여준다.
        emptyResultTextView.visibility = if (isEmptyResult) View.VISIBLE else View.GONE
    }

    // 사용자가 입력하는 것을 감지하는 리스너
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    activity?.runOnUiThread{
                        // 사용자가 입력한 단어
                        val searchWord= s.toString()
                        if (!searchWord.isNullOrEmpty()){
                            // 검색한 단어가 있는 경우
                            query = searchWord
                            // 페이지는 1로 하고 입력한 단어를 검색한다.
                            page = 1
                            viewModel.getSearchResult(searchWord, page)
                            // 스크롤하면 다음 페이지를 가져올 수 있게 한다.
                            scrollListener.searchNewWord()
                        }else{
                            // 검색한 단어가 없는 경우
                            // 리스트 화면도 안보이게 한다.
                            emptyResultTextView.visibility= View.GONE
                            listErrorTextView.visibility = View.GONE
                            imageRecyclerView.visibility = View.GONE
                        }
                    }
                }
            }, 1000)



        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 뷰 모델 생성 및 관찰할 라이브 데이터 설
        viewModel = ViewModelProvider(this).get(ImageListViewModel::class.java)
        viewModel.imageList.observe(viewLifecycleOwner, imageListDataObserver )
        viewModel.isLoading.observe(viewLifecycleOwner, loadingLiveDataObserver)
        viewModel.isLoadError.observe(viewLifecycleOwner, errorLiveDataObserver)
        viewModel.isEmptyResult.observe(viewLifecycleOwner, emptyResultLiveDataObserver)

        imageRecyclerView.apply {
            // 3xN 그리드 모양으로 설정
            layoutManager = GridLayoutManager(context, 3)

            // 스크롤 감지할 리스너 설정
            scrollListener = RecyclerViewScrollListener({ requestMoreImage() }, layoutManager as GridLayoutManager)
            addOnScrollListener(scrollListener)
            adapter = listAdapter

        }

        // 검색 입력 창에 사용자가 입력하는 것을 감지하는 리스너 설정
        searchEditText.addTextChangedListener(textWatcher)

    }

    private fun requestMoreImage() {
        // 다음 페이지를 요청한다.
        page++
        viewModel.getSearchResult(query, page)

    }

    companion object {
        private const val TAG = "ImageListFragment"
    }


}