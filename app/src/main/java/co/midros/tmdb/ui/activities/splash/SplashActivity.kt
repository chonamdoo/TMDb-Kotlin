package co.midros.tmdb.ui.activities.splash

import android.os.Bundle
import android.support.multidex.MultiDex
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectGenders
import co.midros.tmdb.ui.activities.base.BaseActivity
import co.midros.tmdb.utils.DataSharePreference
import co.midros.tmdb.utils.fadeZoomTransitionActivity
import co.midros.tmdb.utils.openActivityMain
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

/**
 * Created by luis rafael on 22/01/18.
 */
class SplashActivity : BaseActivity() {

    @Inject lateinit var interactor: InteractorSplash

    @Inject lateinit var preferences: DataSharePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        component!!.inject(this)
        MultiDex.install(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        fadeZoomTransitionActivity(this, logo_splash)
        onGetGendersMovies()
    }

    override fun onDestroy() {
        clearDisposable()
        super.onDestroy()
    }

    private fun onGetGendersMovies() {
        addDisposable(interactor.onGetGendersMovies()
                .doOnSuccess { setGenderMovies(it) }
                .observeOn(SchedulerProvider.ui())
                .subscribe({ getGendersTv() }, { getGendersTv() }))
    }

    private fun setGenderMovies(data:ObjectGenders){
        (0 until data.genres.size).asSequence().map { data.genres[it] }
                .forEach { preferences.setGenderMovie(it.id.toString(), it.name) }
    }

    private fun getGendersTv() {
        addDisposable(interactor.onGetGendersTv()
                .doOnSuccess { setGenderTv(it) }
                .observeOn(SchedulerProvider.ui())
                .subscribe({ openActivityHome() }, { openActivityHome() }))
    }

    private fun setGenderTv(data: ObjectGenders){
        (0 until data.genres.size).asSequence().map { data.genres[it] }
                .forEach { preferences.setGenderTvShow(it.id.toString(), it.name) }
    }

    private fun openActivityHome() {
        openActivityMain(this)
    }

}