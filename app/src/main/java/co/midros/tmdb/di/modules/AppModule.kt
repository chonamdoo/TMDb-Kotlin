package co.midros.tmdb.di.modules

import android.app.Application
import android.content.Context
import co.midros.tmdb.app.TmdbApp
import co.midros.tmdb.di.AppsContext
import dagger.Module
import dagger.Provides

/**
 * Created by luis rafael on 19/02/18.
 */
@Module
class AppModule(val app: TmdbApp) {

    @Provides
    @AppsContext
    fun provideContext(): Context = app

    @Provides
    fun provideApplication(): Application = app

}