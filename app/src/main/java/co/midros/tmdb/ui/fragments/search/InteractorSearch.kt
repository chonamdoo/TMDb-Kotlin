package co.midros.tmdb.ui.fragments.search

import co.midros.tmdb.api.NewsApi
import co.midros.tmdb.objects.ObjectsSearch
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by luis rafael on 11/02/18.
 */
class InteractorSearch @Inject constructor(private var api: NewsApi) : InterfaceSearch.Interactor {

    override fun getParamsSearch(query: String): MutableMap<String, String> = api.getParamsSearch(1, query)

    override fun getDataSearch(query: String): Single<ObjectsSearch> = api.getDataSearch(getParamsSearch(query))

}