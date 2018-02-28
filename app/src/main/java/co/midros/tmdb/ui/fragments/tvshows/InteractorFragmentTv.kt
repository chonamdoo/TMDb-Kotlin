package co.midros.tmdb.ui.fragments.tvshows

import android.support.v4.app.FragmentManager
import co.midros.tmdb.R
import co.midros.tmdb.ui.fragments.tvshows.category.FragmentCategoryTv
import co.midros.tmdb.utils.ConstStrings
import javax.inject.Inject

/**
 * Created by luis rafael on 28/01/18.
 */
class InteractorFragmentTv @Inject constructor(private var supportFragment: FragmentManager) : InterfaceFragmentTv {


    override fun fragmentCategoryPopular() {
        setFragmentCategory(ConstStrings.POPULAR_TV)
    }

    override fun fragmentCategoryRating() {
        setFragmentCategory(ConstStrings.TOP_RATED_TV)
    }

    override fun fragmentCategoryTvAir() {
        setFragmentCategory(ConstStrings.AIR_TV)
    }

    override fun fragmentCategoryTvToday() {
        setFragmentCategory(ConstStrings.TODAY_TV)
    }

    private fun setFragmentCategory(category: String) {
        val fragmentNow = supportFragment.findFragmentByTag("tv")
        val manager = supportFragment
        val trans = manager.beginTransaction()
        if (fragmentNow != null) {
            trans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            trans.remove(fragmentNow)
        }
        trans.replace(R.id.frame_principal_tv, FragmentCategoryTv().newInstance(category), "tv")
        trans.commitAllowingStateLoss()
    }
}