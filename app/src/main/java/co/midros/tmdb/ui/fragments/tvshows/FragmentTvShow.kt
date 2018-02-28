package co.midros.tmdb.ui.fragments.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.utils.removeShiftMode
import kotlinx.android.synthetic.main.fragment_tv_show.*
import javax.inject.Inject

/**
 * Created by luis rafael on 28/01/18.
 */
class FragmentTvShow : BaseFragment() {

    @Inject
    lateinit var interactor: InteractorFragmentTv

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_tv_show, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setNavigationButton()
    }

    private fun setNavigationButton() {
        interactor.fragmentCategoryPopular()
        removeShiftMode(navigation_tv)
        navigation_tv.setOnNavigationItemReselectedListener {}
        navigation_tv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_popular_tv -> {
                    interactor.fragmentCategoryPopular()
                }
                R.id.nav_top_rating_tv -> {
                    interactor.fragmentCategoryRating()
                }
                R.id.nav_air_tv -> {
                    interactor.fragmentCategoryTvAir()
                }
                R.id.nav_today_tv -> {
                    interactor.fragmentCategoryTvToday()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}