package co.midros.tmdb.ui.activities.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.base.BaseInteractor
import co.midros.tmdb.ui.fragments.movies.FragmentMovies
import co.midros.tmdb.ui.fragments.search.FragmentSearch
import co.midros.tmdb.ui.fragments.tvshows.FragmentTvShow
import co.midros.tmdb.utils.ConstStrings
import javax.inject.Inject

class InteractorMain<V : InterfaceMainView> @Inject constructor(supportFragment: FragmentManager, context: Context) : BaseInteractor<V>(supportFragment, context), InterfaceMainInteractor<V> {

    private var fragmentSearch = FragmentSearch()

    override fun setDefaultFragment() {
        setFragmentMovies()
    }

    override fun setFragmentMovies() {
        getInterfaceView().setDrawerUnLock()
        getInterfaceView().setCheckedNavigationItem(R.id.nav_movies)
        getInterfaceView().closeNavigationDrawer()
        setTitleToolbar(getContext().resources.getString(R.string.movies))
        setFragment(FragmentMovies())
    }

    override fun setFragmentSeries() {
        getInterfaceView().setCheckedNavigationItem(R.id.nav_tv_shows)
        getInterfaceView().closeNavigationDrawer()
        setTitleToolbar(getContext().resources.getString(R.string.tv_shows))
        setFragment(FragmentTvShow())
    }

    override fun setFragmentSearch() {
        getInterfaceView().setDrawerLock()
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .add(R.id.frame_principal, fragmentSearch, ConstStrings.SEARCH_TAG)
                .commit()
    }

    override fun closeFragmentSearch(tag: String) {
        val fragmentManager = getSupportFragmentManager()
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .remove(fragment)
                    .commitNow()
            getInterfaceView().setDrawerUnLock()
        }
    }

    override fun getFragmentSearch(): FragmentSearch = fragmentSearch

    private fun setTitleToolbar(title: String) {
        getInterfaceView().setTitleToolbar(title)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = getSupportFragmentManager()
        val fragmentNow = fragmentManager.findFragmentByTag(ConstStrings.TYPE_FRAGMENT)
        val trans = fragmentManager.beginTransaction()
        if (fragmentNow != null) {
            trans.remove(fragmentNow)
        }
        trans.disallowAddToBackStack()
        trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
        trans.replace(R.id.frame_principal, fragment, ConstStrings.TYPE_FRAGMENT)
        trans.commitNow()

    }
}