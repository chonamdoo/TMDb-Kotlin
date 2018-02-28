package co.midros.tmdb.ui.fragments.details.detailsbottom

import co.midros.tmdb.objects.*

/**
 * Created by luis rafael on 9/02/18.
 */
interface InterfaceDetailsBottomView {

    fun showLoading()
    fun showDataDetails()
    fun showDataDetailsPerson()
    fun hiddenLoading()
    fun hiddenLoadingFailed()
    fun hiddenViews()

    fun setDataMovies(data: PojoDetailsMovies)
    fun setDataTv(data: PojoDetailsTv)
    fun setDataPerson(data: ObjectsPerson)

    fun setCast(list: MutableList<ObjectsDetailsCast>)
    fun setTrailers(list: MutableList<ObjectDetailsTrailers>)
    fun setImages(data: ObjectImages)

    fun setDataSimilarMovie(list: MutableList<PojoResultsMovie>)
    fun setDataSimilarTv(list: MutableList<PojoResultsTv>)

}