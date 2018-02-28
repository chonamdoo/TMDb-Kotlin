package co.midros.tmdb.ui.activities.splash

import co.midros.tmdb.api.NewsApi
import co.midros.tmdb.objects.ObjectGenders
import co.midros.tmdb.utils.*
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by luis rafael on 22/01/18.
 */
class InteractorSplash @Inject constructor(private val api: NewsApi) : InterfaceSplash {

    override fun getParams(): MutableMap<String, String> = api.getParams(1)

    override fun onGetGendersMovies() : Single<ObjectGenders> = api.getGenders(ConstStrings.GENDER_MOVIE,getParams())

    override fun onGetGendersTv() : Single<ObjectGenders> = api.getGenders(ConstStrings.GENDER_TV,getParams())
}