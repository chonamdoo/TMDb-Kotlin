package co.midros.tmdb.ui.fragments.tvshows.category

import co.midros.tmdb.api.NewsApi
import co.midros.tmdb.objects.ObjectTv
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by luis rafael on 28/01/18.
 */
class InteractorCategoryTv @Inject constructor(val api: NewsApi) : InterfaceCategoryTv.Interactor {

    private var category: String? = null

    override fun setCategory(category: String) {
        this.category = category
    }

    override fun getParams(nextPage: Int): MutableMap<String, String> = api.getParams(nextPage)

    override fun getTvShow(nextPage: Int): Single<ObjectTv> = api.getTvShows(category!!, getParams(nextPage))

}