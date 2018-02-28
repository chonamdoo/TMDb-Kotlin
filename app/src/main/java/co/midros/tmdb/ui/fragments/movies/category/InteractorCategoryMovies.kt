package co.midros.tmdb.ui.fragments.movies.category

import co.midros.tmdb.api.NewsApi
import co.midros.tmdb.objects.ObjectMovies
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by luis rafael on 23/01/18.
 */
class InteractorCategoryMovies @Inject constructor(val api: NewsApi) : InterfaceCategoryMovies.Interactor {

    private var category: String? = null

    override fun setCategory(category: String) {
        this.category = category
    }

    override fun getParams(nextPage: Int): MutableMap<String, String> = api.getParams(nextPage)

    override fun getMovies(nextPage: Int): Single<ObjectMovies> = api.getMovies(category!!, getParams(nextPage))

}