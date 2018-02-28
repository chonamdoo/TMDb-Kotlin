package co.midros.tmdb.ui.fragments.search

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectsSearch
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.ui.adapters.RecyclerAdapterSearch
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import com.google.gson.JsonArray
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 11/02/18.
 */
class FragmentSearch :BaseFragment(), InterfaceSearch.View, RecyclerAdapterSearch.OnItemSearchClickListener {

    @Inject lateinit var interactor:InteractorSearch
    @Inject lateinit var adapterSearch: RecyclerAdapterSearch
    @field:Named(ConstStrings.COUNT3) @Inject lateinit var lManagerCount3: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        getActivityComponent()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_search,container,false)

    override fun setSearch(query:String){
        if (query.isEmpty())showEdtEmpty()
        else getDataSearch(query)
    }

    private fun getDataSearch(query: String){
        addDisposable(interactor.getDataSearch(query)
                .observeOn(SchedulerProvider.ui())
                .subscribe({ resultData(it) }, { hiddenLoadingFailed() }))
    }

    private fun resultData(data:ObjectsSearch){
        if (data.results.size() == 0) hiddenLoadingNotData()
        else {
            setDataSearch(data.results)
            hiddenLoading()
        }
    }

    override fun setDataSearch(json: JsonArray) {
        adapterSearch.addItem(json)
        list_search.apply {
            layoutManager = lManagerCount3
            adapter = adapterSearch
            recycledViewPool.clear()
        }
        adapterSearch.notifyDataSetChanged()
        adapterSearch.setOnItemMovieClickListener(this)

    }

    override fun onItemSearchClicked(id: Int, title: String, type: String, image: String) {
        getBaseActivity().setMaximizeDraggable(id,title,type,image)
    }

    override fun showLoading() {
        //progress_search.visibility = View.VISIBLE
    }

    private fun showEdtEmpty() {
        txt_view_search.show()
        //progress_search.visibility = View.GONE
        list_search.hide()
        connect_search.hide()
        txt_no_data.hide()
    }

    override fun hiddenLoading() {
        list_search.show()
        txt_view_search.hide()
        //progress_search.hide()
        connect_search.hide()
        txt_no_data.hide()
    }

    override fun hiddenLoadingFailed() {
        txt_view_search.hide()
        //progress_search.hide()
        list_search.hide()
        txt_no_data.hide()
        connect_search.show()
    }

    override fun hiddenLoadingNotData() {
        txt_view_search.hide()
        //progress_search.hide()
        list_search.hide()
        connect_search.hide()
        txt_no_data.show()
    }
}