package co.midros.tmdb.api

import co.midros.tmdb.objects.*
import co.midros.tmdb.utils.ConstStrings.Companion.DETAILS_MOVIE
import co.midros.tmdb.utils.ConstStrings.Companion.DETAILS_PERSON
import co.midros.tmdb.utils.ConstStrings.Companion.DETAILS_TV
import co.midros.tmdb.utils.ConstStrings.Companion.MOVIE_ID
import co.midros.tmdb.utils.ConstStrings.Companion.PERSON_ID
import co.midros.tmdb.utils.ConstStrings.Companion.SEARCH
import co.midros.tmdb.utils.ConstStrings.Companion.SIMILAR_MOVIE
import co.midros.tmdb.utils.ConstStrings.Companion.SIMILAR_TV
import co.midros.tmdb.utils.ConstStrings.Companion.TV_ID
import io.reactivex.Single
import retrofit2.http.*

interface RestApi {

    @GET
    fun getGenders(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectGenders>

    @GET
    fun getMovies(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectMovies>

    @GET
    fun getTvShows(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectTv>

    @GET
    fun getImages(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectImages>

    @GET
    fun getTrailer(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectTrailers>

    @GET(DETAILS_MOVIE)
    fun getDetailsMovies(@Path(MOVIE_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<PojoDetailsMovies>

    @GET(DETAILS_TV)
    fun getDetailsTv(@Path(TV_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<PojoDetailsTv>

    @GET(DETAILS_PERSON)
    fun getDetailsPerson(@Path(PERSON_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<ObjectsPerson>

    @GET(SIMILAR_MOVIE)
    fun getSimilarMovie(@Path(MOVIE_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<ObjectMovies>

    @GET(SIMILAR_TV)
    fun getSimilarTv(@Path(TV_ID) id: Int, @QueryMap params: MutableMap<String, String>): Single<ObjectTv>

    @GET(SEARCH)
    fun getDataSearch(@QueryMap params: MutableMap<String, String>): Single<ObjectsSearch>

    @GET
    fun getCast(@Url url: String, @QueryMap params: MutableMap<String, String>): Single<ObjectsCast>

}