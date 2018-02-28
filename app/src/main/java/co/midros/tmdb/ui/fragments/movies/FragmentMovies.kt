package co.midros.tmdb.ui.fragments.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.utils.removeShiftMode
import kotlinx.android.synthetic.main.fragment_movies.*
import javax.inject.Inject

/**
 * Created by luis rafael on 22/01/18.
 */
class FragmentMovies : BaseFragment() {

    @Inject
    lateinit var interactor: InteractorFragmentMovies


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_movies, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setNavigationButton()
    }

    private fun setNavigationButton() {
        interactor.fragmentCategoryPopular()
        removeShiftMode(navigation_movies)
        navigation_movies.setOnNavigationItemReselectedListener {}
        navigation_movies.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular_movies -> {
                    interactor.fragmentCategoryPopular()
                }
                R.id.nav_top_rating_movies -> {
                    interactor.fragmentCategoryRating()
                }
                R.id.nav_upcoming_movies -> {
                    interactor.fragmentCategoryUpcoming()
                }
                R.id.nav_now_playing -> {
                    interactor.fragmentCategoryNowPlaying()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }


}