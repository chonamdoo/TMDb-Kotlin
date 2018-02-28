package co.midros.tmdb.ui.fragments.movies

import android.support.v4.app.FragmentManager
import co.midros.tmdb.R
import co.midros.tmdb.ui.fragments.movies.category.FragmentCategoryMovies
import co.midros.tmdb.utils.ConstStrings
import javax.inject.Inject

/**
 * Created by luis rafael on 22/01/18.
 */
class InteractorFragmentMovies @Inject constructor(private var supportFragment: FragmentManager) : InterfaceFragmentMovies {

    override fun fragmentCategoryPopular() {
        setFragmentCategory(ConstStrings.POPULAR_MOVIE)
    }

    override fun fragmentCategoryRating() {
        setFragmentCategory(ConstStrings.TOP_RATED_MOVIE)
    }

    override fun fragmentCategoryUpcoming() {
        setFragmentCategory(ConstStrings.UPCOMING_MOVIE)
    }

    override fun fragmentCategoryNowPlaying() {
        setFragmentCategory(ConstStrings.NOW_PLAYING)
    }

    private fun setFragmentCategory(category: String) {
        val fragmentNow = supportFragment.findFragmentByTag("movies")
        val manager = supportFragment
        val trans = manager.beginTransaction()
        if (fragmentNow != null) {
            trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            trans.remove(fragmentNow)
        }
        trans.replace(R.id.frame_principal_movies, FragmentCategoryMovies().newInstance(category), "movies")
        trans.commitAllowingStateLoss()
    }

}