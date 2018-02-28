package co.midros.tmdb.ui.fragments.details.detailstop

import co.midros.tmdb.objects.ObjectDetailsTrailers

/**
 * Created by luis rafael on 10/02/18.
 */
interface InterfaceDetailsTopView {

    fun setDetailsTop(id: Int, name: String, type: String, image: String)
    fun setCueVideo(key: String)

    fun showLoading()
    fun hiddenLoading()
    fun hiddenFailed()

    fun setImage()
    fun setTrailer(data: ObjectDetailsTrailers)
    fun setPausePlayer()
    fun showMenuDetailsTop()
    fun hiddenMenuDetailsTop()
    fun showPlayer()
    fun hiddenViewsPlayer()
    fun showViewsPlayer()
    fun getIdDetails(): Int


}