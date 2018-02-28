package co.midros.tmdb.ui.fragments.tvshows.category

import co.midros.tmdb.objects.ObjectTv
import io.reactivex.Single

/**
 * Created by luis rafael on 28/01/18.
 */
interface InterfaceCategoryTv {


    interface Interactor {
        fun setCategory(category: String)
        fun getParams(nextPage: Int): MutableMap<String, String>
        fun getTvShow(nextPage: Int): Single<ObjectTv>

    }

    interface ViewCategoryTv {
        fun setAdapterRecycler()
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()

    }

}