package co.midros.tmdb.ui.fragments.search

import co.midros.tmdb.objects.ObjectsSearch
import com.google.gson.JsonArray
import io.reactivex.Single

/**
 * Created by luis rafael on 11/02/18.
 */
interface InterfaceSearch {

    interface Interactor {
        fun getParamsSearch(query: String): MutableMap<String, String>
        fun getDataSearch(query: String): Single<ObjectsSearch>
    }

    interface View {
        fun setSearch(query: String)
        fun setDataSearch(json: JsonArray)
        fun showLoading()
        fun hiddenLoading()
        fun hiddenLoadingFailed()
        fun hiddenLoadingNotData()
    }

}