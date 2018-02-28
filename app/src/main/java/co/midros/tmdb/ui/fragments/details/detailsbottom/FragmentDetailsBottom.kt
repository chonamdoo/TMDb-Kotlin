package co.midros.tmdb.ui.fragments.details.detailsbottom

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.ImagesActivity
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.ui.adapters.*
import co.midros.tmdb.ui.fragments.details.InteractorFragmentDetails
import co.midros.tmdb.objects.*
import co.midros.tmdb.utils.*
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import com.daimajia.androidanimations.library.Techniques
import com.pawegio.kandroid.*
import kotlinx.android.synthetic.main.fragment_details_bottom.*
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by luis rafael on 9/02/18.
 */
class FragmentDetailsBottom : BaseFragment(), InterfaceDetailsBottomView {

    @Inject lateinit var adapterCast: RecyclerAdapterCast
    @Inject lateinit var adapterImages: RecyclerAdapterImages
    @Inject lateinit var adapterTrailers: RecyclerAdapterTrailers
    @Inject lateinit var adapterSimilarMovie: RecyclerAdapterSimilarMovie
    @Inject lateinit var adapterSimilarTv: RecyclerAdapterSimilarTv

    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerCast: GridLayoutManager
    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerTrailers: GridLayoutManager
    @field:Named(ConstStrings.HORIZONTAL)
    @Inject lateinit var lManagerImages: GridLayoutManager
    @field:Named(ConstStrings.COUNT3)
    @Inject lateinit var lManagerCount3: GridLayoutManager

    @Inject lateinit var interactor: InteractorFragmentDetails

    private var id: Int? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_details_bottom, container, false)


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onClickConetFailed()
    }

    private fun onClickConetFailed() {
        connect_details_bottom.setOnClickListener {
            setDetailsBottom(id!!, type!!)
        }
    }

    fun setDetailsBottom(id: Int, type: String) {
        this.id = id
        this.type = type
        when (type) {
            ConstStrings.MOVIE -> {
                getDetailsMovie(id)
                getCast("movie/$id/credits")
                getTrailers("movie/$id/videos")
                getImages("movie/$id/images")
                getSimilarMovie(id)
            }
            ConstStrings.TV -> {
                getDetailsTv(id)
                getCast("tv/$id/credits")
                getTrailers("tv/$id/videos")
                getImages("tv/$id/images")
                getSimilarTv(id)
            }
            ConstStrings.PERSON -> {
                getDetailsPerson(id)
            }
        }
    }

    private fun getDetailsMovie(id: Int) {
        addDisposable(interactor.getDetailsMovie(id)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { showLoading() }
                .doFinally { hiddenLoading() }
                .subscribe({ setDataMovies(it) }, { hiddenLoadingFailed() }))
    }

    private fun getDetailsTv(id: Int) {
        addDisposable(interactor.getDetailsTv(id)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { showLoading() }
                .doFinally { hiddenLoading() }
                .subscribe({ setDataTv(it) }, { hiddenLoadingFailed() }))
    }

    private fun getDetailsPerson(id: Int) {
        addDisposable(interactor.getDetailsPerson(id)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { showLoading() }
                .doFinally { hiddenLoading() }
                .subscribe({ setDataPerson(it) }, { hiddenLoadingFailed() }))
    }

    private fun getCast(url: String) {
        addDisposable(interactor.getCast(url)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { adapterCast.clearList() }
                .subscribe({ setCast(it.cast) }, { e(it) }))
    }

    private fun getSimilarTv(id: Int) {
        addDisposable(interactor.getSimilarTv(id)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { adapterSimilarTv.clearList() }
                .subscribe({ setDataSimilarTv(it.results) }, { e(it) }))
    }

    private fun getSimilarMovie(id: Int) {
        addDisposable(interactor.getSimilarMovie(id)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { adapterSimilarMovie.clearList() }
                .subscribe({ setDataSimilarMovie(it.results) }, { e(it) }))
    }

    private fun getImages(url: String) {
        addDisposable(interactor.getImages(url)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { adapterImages.clearList() }
                .subscribe({ setImages(it) }, { e(it) }))
    }

    private fun getTrailers(url: String) {
        addDisposable(interactor.getTrailers(url)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { adapterTrailers.clearList() }
                .subscribe({ setTrailers(it.results) }, { e(it) }))
    }

    override fun showLoading() {
        hiddenViews()
        progress_details_bottom.show()
    }

    override fun showDataDetails(){
        data_details_bottom.show()
        animatedView(Techniques.FadeIn, data_details_bottom, 1000)
    }

    override fun showDataDetailsPerson() {
        datos_details_person_bottom.show()
        animatedView(Techniques.FadeIn, datos_details_person_bottom, 1000)
    }

    override fun hiddenLoading() { progress_details_bottom.hide() }

    override fun hiddenLoadingFailed() {
        progress_details_bottom.hide()
        connect_details_bottom.show()
    }

    override fun hiddenViews() {
        data_details_bottom.hide()
        datos_details_person_bottom.hide()
        rating_progress_details_bottom.setValue(0.0f)
        view_movie_bottom.hide()
        view_tv_bottom.hide()
        view_cast_bottom.hide()
        view_images_bottom.hide()
        view_trailer_bottom.hide()
        view_similar_bottom.hide()
        connect_details_bottom.hide()
    }

    @SuppressLint("SetTextI18n")
    override fun setDataMovies(data: PojoDetailsMovies) {
        showDataDetails()
        view_movie_bottom.show()
        fecha_details_bottom.text = if (data.release_date != null) getReformatDate(data.release_date) else "-"
        voto_details_bottom.text = data.vote_count.toString()
        rating_progress_details_bottom.setValueAnimated(data.vote_average.toString().replace(".", "").toFloat(), 1500)
        status_details_bottom.text = if (!data.status.isEmpty()) data.status else "-"
        runtime_details_bottom.text = "${data.runtime}m"
        budget_details_bottom.text = getFormattedDollars(data.budget)
        revenue_details_bottom.text = getFormattedDollars(data.revenue)
        overview_details_bottom.text = if (!data.overview.isEmpty()) data.overview else "-"
        homepage_details_bottom.text = if (!data.homepage.isEmpty()) data.homepage else "-"
        if (homepage_details_bottom.text != "-") addLinks(homepage_details_bottom, data.homepage, data.homepage)

        val genders = StringBuilder()
        (0 until data.genres.size).asSequence().map { data.genres[it] }
                .forEach { genders.append(it.name).append(" ") }
        genero_details_bottom.text = genders.toString()
    }

    override fun setDataTv(data: PojoDetailsTv) {
        showDataDetails()
        view_tv_bottom.show()
        fecha_details_bottom.text = if (data.first_air_date != null) getReformatDate(data.first_air_date) else "-"
        voto_details_bottom.text = data.vote_count.toString()
        rating_progress_details_bottom.setValueAnimated(data.vote_average.toString().replace(".", "").toFloat(), 1500)
        status_details_bottom.text = if (!data.status.isEmpty()) data.status else "-"
        txt_seasons_bottom.text = data.number_of_seasons.toString()
        txt_episodes_bottom.text = data.number_of_episodes.toString()
        overview_details_bottom.text = if (!data.overview.isEmpty()) data.overview else "-"
        homepage_details_bottom.text = if (!data.homepage.isEmpty()) data.homepage else "-"
        if (homepage_details_bottom.text != "-") addLinks(homepage_details_bottom, data.homepage, data.homepage)

        val runtime = StringBuilder()
        (0 until data.episode_run_time.size).asSequence().map { data.episode_run_time[it] }
                .forEach { runtime.append(it).append("m ") }
        runtime_details_bottom.text = runtime

        val genders = StringBuilder()
        (0 until data.genres.size).asSequence().map { data.genres[it] }
                .forEach { genders.append(it.name).append(" ") }
        genero_details_bottom.text = genders.toString()
    }

    override fun setDataPerson(data: ObjectsPerson) {
        showDataDetailsPerson()
        birthday_person_bottom.text = if (data.birthday != null) getReformatDate(data.birthday) else "-"
        place_of_birth_person_bottom.text = if (data.place_of_birth != null) data.place_of_birth else "-"
        biography_person_bottom.text = if (!data.biography.isEmpty()) data.biography else "-"
    }

    override fun setCast(list: MutableList<ObjectsDetailsCast>) {
        if (!list.isEmpty()) {
            for (cast in list) adapterCast.addItem(cast)
            view_cast_bottom.show()
            animatedView(Techniques.FadeIn, view_cast_bottom, 1000)
        }
        list_cast_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerCast
            isNestedScrollingEnabled = false
            adapter = adapterCast
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_cast_bottom, 1000)
        adapterCast.notifyDataSetChanged()
        setOnItemCastClick()
    }

    private fun setOnItemCastClick() {
        adapterCast.setOnItemCastClickListener(object : RecyclerAdapterCast.OnItemCastClickListener {
            override fun onItemCastClicked(id: Int, title: String, type: String, image: String) {
                if (image == "") showMessage(title)
                else getBaseActivity().setMaximizeDraggable(id, title, type, image)
            }
        })
    }

    override fun setTrailers(list: MutableList<ObjectDetailsTrailers>) {
        if (!list.isEmpty()) {
            for (trailers in list) adapterTrailers.addItem(trailers)
            view_trailer_bottom.show()
            animatedView(Techniques.FadeIn, view_trailer_bottom, 1000)
        }
        list_trailers_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerTrailers
            isNestedScrollingEnabled = false
            adapter = adapterTrailers
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_trailers_bottom, 1000)
        adapterTrailers.notifyDataSetChanged()
        setOnItemTrailersClick()
    }

    private fun setOnItemTrailersClick() {
        adapterTrailers.setOnItemCastClickListener(object : RecyclerAdapterTrailers.OnItemTrailersClickListener {
            override fun onItemTrailersClicked(key_video: String) {
                getBaseActivity().setCueVideo(key_video)
            }
        })
    }

    override fun setImages(data: ObjectImages) {
        if (!data.backdrops.isEmpty()) {
            for (images in data.backdrops) adapterImages.addItem(images)
            view_images_bottom.show()
            animatedView(Techniques.FadeIn, view_images_bottom, 1000)
        }
        list_images_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerImages
            isNestedScrollingEnabled = false
            adapter = adapterImages
            recycledViewPool.clear()
        }
        animatedView(Techniques.SlideInRight, list_images_bottom, 1000)
        adapterImages.notifyDataSetChanged()
        onClickAllImage(data)
    }

    private fun onClickAllImage(data: ObjectImages) {
        btn_see_all_images.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("data", data as Serializable)
            val intent = IntentFor<ImagesActivity>(activity!!)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    override fun setDataSimilarMovie(list: MutableList<PojoResultsMovie>) {
        if (!list.isEmpty()) {
            for (movie in list) adapterSimilarMovie.addItem(movie)
            setAdapterRecycler(adapterSimilarMovie)
            setOnItemMoviesClick()
        }
    }

    private fun setOnItemMoviesClick() {
        adapterSimilarMovie.setOnItemMovieClickListener(object : RecyclerAdapterSimilarMovie.OnItemMovieClickListener {
            override fun onItemMovieClicked(id: Int, title: String, type: String, image: String) {
                if (image == "") showMessage(title)
                else getBaseActivity().setMaximizeDraggable(id, title, type, image)
            }
        })
    }

    override fun setDataSimilarTv(list: MutableList<PojoResultsTv>) {
        if (!list.isEmpty()) {
            for (tv in list) adapterSimilarTv.addItem(tv)
            setAdapterRecycler(adapterSimilarTv)
            setOnItemTvClick()
        }
    }

    private fun setOnItemTvClick() {
        adapterSimilarTv.setOnItemTvClickListener(object : RecyclerAdapterSimilarTv.OnItemTvClickListener {
            override fun onItemTvClicked(id: Int, title: String, type: String, image: String) {
                if (image == "") showMessage(title)
                else getBaseActivity().setMaximizeDraggable(id, title, type, image)
            }
        })
    }

    private fun setAdapterRecycler(adapters: RecyclerView.Adapter<*>) {
        view_similar_bottom.show()
        list_similar_bottom.apply {
            setHasFixedSize(false)
            layoutManager = lManagerCount3
            isNestedScrollingEnabled = false
            adapter = adapters
            recycledViewPool.clear()
        }
        adapters.notifyDataSetChanged()
    }

}