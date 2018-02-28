package co.midros.tmdb.ui.fragments.movies.category

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectMovies
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.ui.adapters.RecyclerAdapterMovies
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.OnScrollListenerUtils
import co.midros.tmdb.utils.animatedView
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import com.daimajia.androidanimations.library.Techniques
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_category_movies.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 22/01/18.
 */
class FragmentCategoryMovies : BaseFragment(), InterfaceCategoryMovies.ViewCategoryMovies, RecyclerAdapterMovies.OnItemMovieClickListener {

    @Inject lateinit var interactor: InteractorCategoryMovies

    @Inject lateinit var adapterRecycler: RecyclerAdapterMovies

    @field:Named(ConstStrings.DEFAULT)
    @Inject lateinit var lManager: GridLayoutManager

    private var nextPage: Int = 1

    fun newInstance(category: String): FragmentCategoryMovies {
        val fragment = FragmentCategoryMovies()
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
            inflater.inflate(R.layout.fragment_category_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        interactor.setCategory(arguments!!.getString(ConstStrings.CATEGORY))
        reloadConnectMovies()
        setAdapterRecycler()
        getMovies()
    }

    private fun getMovies(showLoading:Boolean = true) {
        addDisposable(interactor.getMovies(nextPage)
                .doOnSuccess { nextPage = it.page + 1 }
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (showLoading) showLoading() }
                .doFinally { hiddenLoading() }
                .subscribe({ addItemRecycler(it) }, { hiddenLoadingFailed() }))
    }

    private fun addItemRecycler(data:ObjectMovies){
        for (movie in data.results) adapterRecycler.addItem(movie)
        showList()
    }

    private fun reloadConnectMovies() {
        connect_movies.setOnClickListener {
            getMovies()
        }
    }

    override fun setAdapterRecycler() {
        list_movies.apply {
            setHasFixedSize(true)
            layoutManager = lManager
            recycledViewPool.clear()
            adapter = adapterRecycler
            addOnScrollListener(OnScrollListenerUtils({ getMovies() }, lManager))
        }
        adapterRecycler.notifyDataSetChanged()
        adapterRecycler.setOnItemMovieClickListener(this)
    }

    override fun onItemMovieClicked(id: Int, title: String, type: String, image: String) {
        getBaseActivity().setMaximizeDraggable(id, title, type, image)
    }

    override fun showLoading() {
        connect_movies.hide()
        progress_movies.show()
        animatedView(Techniques.FadeIn, progress_movies, 100)
    }

    private fun showList(){
        runDelayedOnUiThread(200) {
            if (progress_movies != null) progress_movies.hide()
            if (list_movies != null) list_movies.show()
        }
    }

    override fun hiddenLoading() {
        if (progress_movies != null) animatedView(Techniques.SlideOutDown, progress_movies, 200)
    }

    override fun hiddenLoadingFailed() {
        if (progress_movies != null) progress_movies.hide()
        if (connect_movies != null) connect_movies.show()
    }

}