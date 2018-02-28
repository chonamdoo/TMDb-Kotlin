package co.midros.tmdb.app

import android.app.Application
import co.midros.tmdb.di.components.AppsComponent
import co.midros.tmdb.di.components.DaggerAppsComponent
import co.midros.tmdb.di.modules.AppModule

/**
 * Created by luis rafael on 17/02/18.
 */
class TmdbApp : Application() {

    companion object {
        @JvmStatic lateinit var component: AppsComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppsComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }


}