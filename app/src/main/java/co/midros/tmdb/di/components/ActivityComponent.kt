package co.midros.tmdb.di.components

import co.midros.tmdb.ui.activities.main.MainActivity
import co.midros.tmdb.ui.activities.splash.SplashActivity
import co.midros.tmdb.di.PerActivity
import co.midros.tmdb.di.modules.ActivityModule
import co.midros.tmdb.di.modules.NetModule
import co.midros.tmdb.ui.fragments.details.detailsbottom.FragmentDetailsBottom
import co.midros.tmdb.ui.fragments.details.detailstop.FragmentDetailsTop
import co.midros.tmdb.ui.fragments.movies.FragmentMovies
import co.midros.tmdb.ui.fragments.movies.category.FragmentCategoryMovies
import co.midros.tmdb.ui.fragments.search.FragmentSearch
import co.midros.tmdb.ui.fragments.tvshows.FragmentTvShow
import co.midros.tmdb.ui.fragments.tvshows.category.FragmentCategoryTv
import dagger.Component

/**
 * Created by luis rafael on 19/02/18.
 */
@PerActivity
@Component(dependencies = arrayOf(AppsComponent::class), modules = arrayOf(NetModule::class, ActivityModule::class))
interface ActivityComponent {

    fun inject(splashActivity: SplashActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(fragmentDetailsTop: FragmentDetailsTop)

    fun inject(fragmentDetailsBottom: FragmentDetailsBottom)

    fun inject(fragmentMovies: FragmentMovies)

    fun inject(fragmentCategoryMovies: FragmentCategoryMovies)

    fun inject(fragmentTvShow: FragmentTvShow)

    fun inject(fragmentCategoryTv: FragmentCategoryTv)

    fun inject(fragmentSearch: FragmentSearch)

}