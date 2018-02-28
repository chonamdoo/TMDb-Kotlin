package co.midros.tmdb.ui.activities.splash

import co.midros.tmdb.objects.ObjectGenders
import io.reactivex.Single

/**
 * Created by luis rafael on 22/01/18.
 */
interface InterfaceSplash {
    fun getParams(): MutableMap<String, String>
    fun onGetGendersMovies(): Single<ObjectGenders>
    fun onGetGendersTv(): Single<ObjectGenders>
}