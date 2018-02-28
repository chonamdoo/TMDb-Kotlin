package co.midros.tmdb.ui.fragments.details

import co.midros.tmdb.api.NewsApi
import co.midros.tmdb.objects.ObjectsPerson
import co.midros.tmdb.objects.ObjectsCast
import co.midros.tmdb.objects.ObjectImages
import co.midros.tmdb.objects.ObjectMovies
import co.midros.tmdb.objects.PojoDetailsMovies
import co.midros.tmdb.objects.ObjectTrailers
import co.midros.tmdb.objects.ObjectTv
import co.midros.tmdb.objects.PojoDetailsTv
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.getApiKeyTmdb
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by luis rafael on 9/02/18.
 */
class InteractorFragmentDetails @Inject constructor(val api: NewsApi) : InterfaceFragmentDetails {

    override fun getParams(): MutableMap<String, String> = api.getParams(1)

    override fun getDetailsMovie(id: Int): Single<PojoDetailsMovies> = api.getDetailsMovies(id, getParams())

    override fun getDetailsTv(id: Int): Single<PojoDetailsTv> = api.getDetailsTv(id, getParams())

    override fun getDetailsPerson(id: Int): Single<ObjectsPerson> = api.getDetailsPerson(id, getParams())

    override fun getCast(url: String): Single<ObjectsCast> = api.getCast(url, getParams())

    override fun getTrailers(url: String): Single<ObjectTrailers> = api.getTrailer(url, getParams())

    override fun getSimilarMovie(id: Int): Single<ObjectMovies> = api.getSimilarMovie(id, getParams())

    override fun getSimilarTv(id: Int): Single<ObjectTv> = api.getSimilarTv(id, getParams())

    override fun getImages(url: String): Single<ObjectImages> {
        val params = mutableMapOf<String, String>()
        params[ConstStrings.API_KEY] = getApiKeyTmdb()
        return api.getImages(url, params)
    }

}