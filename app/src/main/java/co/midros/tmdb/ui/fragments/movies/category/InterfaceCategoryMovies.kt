package co.midros.tmdb.ui.fragments.movies.category

import co.midros.tmdb.objects.ObjectMovies
import io.reactivex.Single

/**
 * Created by luis rafael on 23/01/18.
 */
interface InterfaceCategoryMovies {

    interface Interactor {
        fun setCategory(category: String)
        fun getParams(nextPage: Int): MutableMap<String, String>
        fun getMovies(nextPage: Int): Single<ObjectMovies>
    }

    interface ViewCategoryMovies {
        fun setAdapterRecycler()
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()
    }
}