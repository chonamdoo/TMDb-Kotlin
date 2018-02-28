package co.midros.tmdb.api

import co.midros.tmdb.objects.*
import io.reactivex.Single

/**
 * Created by luis rafael on 19/02/18.
 */
interface NewsApi {

    fun getParams(page: Int): MutableMap<String, String>

    fun getParamsSearch(page: Int, query: String): MutableMap<String, String>

    fun getGenders(url: String, params: MutableMap<String, String>): Single<ObjectGenders>

    fun getMovies(url: String, params: MutableMap<String, String>): Single<ObjectMovies>

    fun getTvShows(url: String, params: MutableMap<String, String>): Single<ObjectTv>

    fun getImages(url: String, params: MutableMap<String, String>): Single<ObjectImages>

    fun getTrailer(url: String, params: MutableMap<String, String>): Single<ObjectTrailers>

    fun getDetailsMovies(id: Int, params: MutableMap<String, String>): Single<PojoDetailsMovies>

    fun getDetailsTv(id: Int, params: MutableMap<String, String>): Single<PojoDetailsTv>

    fun getDetailsPerson(id: Int, params: MutableMap<String, String>): Single<ObjectsPerson>

    fun getSimilarMovie(id: Int, params: MutableMap<String, String>): Single<ObjectMovies>

    fun getSimilarTv(id: Int, params: MutableMap<String, String>): Single<ObjectTv>

    fun getDataSearch(params: MutableMap<String, String>): Single<ObjectsSearch>

    fun getCast(url: String, params: MutableMap<String, String>): Single<ObjectsCast>

}