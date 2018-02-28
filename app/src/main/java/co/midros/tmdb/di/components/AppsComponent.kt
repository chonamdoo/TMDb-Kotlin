package co.midros.tmdb.di.components

import co.midros.tmdb.app.TmdbApp
import co.midros.tmdb.di.modules.AppModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by luis rafael on 19/02/18.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppsComponent {
    fun inject(app: TmdbApp)
}