package co.midros.tmdb.ui.fragments.details

import co.midros.tmdb.objects.*
import io.reactivex.Single

/**
 * Created by luis rafael on 22/02/18.
 */
interface InterfaceFragmentDetails {
    fun getParams(): MutableMap<String, String>
    fun getDetailsMovie(id: Int): Single<PojoDetailsMovies>
    fun getDetailsTv(id: Int): Single<PojoDetailsTv>
    fun getDetailsPerson(id: Int): Single<ObjectsPerson>
    fun getCast(url: String): Single<ObjectsCast>
    fun getTrailers(url: String): Single<ObjectTrailers>
    fun getImages(url: String): Single<ObjectImages>
    fun getSimilarMovie(id: Int): Single<ObjectMovies>
    fun getSimilarTv(id: Int): Single<ObjectTv>
}