package co.midros.tmdb.ui.activities.main

import co.midros.tmdb.ui.activities.base.InterfaceInteractor
import co.midros.tmdb.di.PerActivity
import co.midros.tmdb.ui.fragments.search.FragmentSearch

/**
 * Created by luis rafael on 20/02/18.
 */
@PerActivity
interface InterfaceMainInteractor<V : InterfaceMainView> : InterfaceInteractor<V> {

    fun setDefaultFragment()
    fun setFragmentMovies()
    fun setFragmentSeries()
    fun setFragmentSearch()
    fun closeFragmentSearch(tag: String)
    fun getFragmentSearch(): FragmentSearch

}