package co.midros.tmdb.ui.fragments.tvshows.category

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectTv
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.ui.adapters.RecyclerAdapterTvShows
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.OnScrollListenerUtils
import co.midros.tmdb.utils.animatedView
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import com.daimajia.androidanimations.library.Techniques
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_category_movies.*
import kotlinx.android.synthetic.main.fragment_category_tv_show.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 28/01/18.
 */
class FragmentCategoryTv : BaseFragment(), InterfaceCategoryTv.ViewCategoryTv, RecyclerAdapterTvShows.OnItemTvClickListener {

    @Inject lateinit var interactor: InteractorCategoryTv

    @Inject lateinit var adapterRecycler: RecyclerAdapterTvShows

    @field:Named(ConstStrings.DEFAULT)
    @Inject lateinit var lManager: GridLayoutManager

    private var nextPage: Int = 1

    fun newInstance(category: String): FragmentCategoryTv {
        val fragment = FragmentCategoryTv()
        val bundle = Bundle()
        bundle.putString(ConstStrings.CATEGORY, category)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_category_tv_show, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        interactor.setCategory(arguments!!.getString(ConstStrings.CATEGORY))
        reloadConnectTvShow()
        setAdapterRecycler()
        getTvShows()
    }

    private fun getTvShows(showLoading:Boolean = true) {
        addDisposable(interactor.getTvShow(nextPage)
                .doOnSuccess { nextPage = it.page + 1 }
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (showLoading) showLoading() }
                .doFinally { hiddenLoading() }
                .subscribe({ addItemRecycler(it) }, { hiddenLoadingFailed() }))
    }

    private fun addItemRecycler(data:ObjectTv){
        for (tv in data.results) adapterRecycler.addItem(tv)
        showList()
    }

    private fun reloadConnectTvShow() {
        connect_tv.setOnClickListener {
            getTvShows()
        }
    }

    override fun setAdapterRecycler() {
        list_tv_show.apply {
            setHasFixedSize(true)
            layoutManager = lManager
            recycledViewPool.clear()
            adapter = adapterRecycler
            addOnScrollListener(OnScrollListenerUtils({ getTvShows() }, lManager))
        }
        adapterRecycler.notifyDataSetChanged()
        adapterRecycler.setOnItemMovieClickListener(this)
    }

    override fun onItemTvClicked(id: Int, title: String, type: String, image: String) {
        getBaseActivity().setMaximizeDraggable(id, title, type, image)
    }

    override fun showLoading() {
        connect_tv.hide()
        progress_tv.show()
        animatedView(Techniques.FadeIn, progress_tv, 100)
    }

    private fun showList(){
        runDelayedOnUiThread(200) {
            if (progress_tv != null) progress_tv.hide()
            if (list_tv_show != null) list_tv_show.show()
        }
    }

    override fun hiddenLoading() {
        if (progress_tv != null) animatedView(Techniques.SlideOutDown, progress_tv, 200)
    }

    override fun hiddenLoadingFailed() {
        if (progress_tv != null) progress_tv.hide()
        if (connect_tv != null) connect_tv.show()
    }
}