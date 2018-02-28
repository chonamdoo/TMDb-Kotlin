package co.midros.tmdb.ui.fragments.details.detailstop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.base.BaseFragment
import co.midros.tmdb.ui.fragments.details.InteractorFragmentDetails
import co.midros.tmdb.objects.ObjectDetailsTrailers
import co.midros.tmdb.objects.ObjectTrailers
import co.midros.tmdb.utils.*
import co.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_YOUTUBE
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.show
import io.reactivex.rxkotlin.toObservable
import kotlinx.android.synthetic.main.fragment_details_top.*
import javax.inject.Inject

/**
 * Created by luis rafael on 9/02/18.
 */
class FragmentDetailsTop : BaseFragment(), InterfaceDetailsTopView, YouTubePlayer.OnInitializedListener, YouTubePlayer.PlayerStateChangeListener, YouTubePlayer.PlaybackEventListener, PopupMenu.OnMenuItemClickListener {

    private var player: YouTubePlayer? = null
    private var popupMenu: PopupMenu? = null
    private var idDetails = 0
    private var image: String? = null
    private var key: String? = null
    private var name: String? = null
    private var checkImage = false

    @Inject lateinit var youtubeFragment: YouTubePlayerSupportFragment
    @Inject lateinit var interactor: InteractorFragmentDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent()!!.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_details_top, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeYoutubeFragment()
        initializePopupMenu()
        initView()
    }

    private fun initializeYoutubeFragment() {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_fragment_top, youtubeFragment).commit()
    }

    private fun initializePopupMenu() {
        popupMenu = PopupMenu(activity, click_menu_top)
        popupMenu!!.menuInflater.inflate(R.menu.popup_menu_details_top, popupMenu!!.menu)
        popupMenu!!.setOnMenuItemClickListener(this)
    }

    private fun initView() {
        click_keyboard_top.setOnClickListener {
            getBaseActivity().setMinimizeDraggable()
        }
        click_trailers_top.setOnClickListener {
            if (player != null) {
                player!!.play()
                showPlayer()
            }
        }
        click_menu_top.setOnClickListener {
            if (popupMenu != null) popupMenu!!.show()
        }
    }

    override fun getIdDetails(): Int = idDetails

    override fun setDetailsTop(id: Int, name: String, type: String, image: String) {
        this.idDetails = id
        this.image = image
        title_details_top.text = name
        setPausePlayer()
        setTypeTrailer(id, type)
    }

    private fun setTypeTrailer(id: Int, type: String) {
        when (type) {
            ConstStrings.MOVIE -> getTrailers("movie/$id/videos")
            ConstStrings.TV -> getTrailers("tv/$id/videos")
            ConstStrings.PERSON -> setImage()
        }
    }

    private fun getTrailers(url: String,showLoading:Boolean=true) {
        addDisposable(interactor.getTrailers(url)
                .observeOn(SchedulerProvider.ui())
                .doOnSubscribe { if (showLoading) showLoading() }
                .subscribe({ resultData(it) }, { hiddenFailed() }))
    }

    private fun resultData(data: ObjectTrailers){
        if (!data.results.isEmpty()) setTrailer(data.results[0])
        else setImage()
    }

    override fun setCueVideo(key: String) {
        showPlayer()
        if (player != null) {
            player!!.cueVideo(key)
            player!!.play()
        }
    }

    override fun showLoading() {
        progressDetails(true)
        fragmentYoutube(false)
        clickTrailers(false)
        imageTrailers(false)
        failedDetails(false)
    }

    override fun hiddenLoading() {
        progressDetails(false)
        clickTrailers(true)
        imageTrailers(true)
        fragmentYoutube(true)
    }

    override fun hiddenFailed() {
        progressDetails(false)
        failedDetails(true)
    }

    override fun setImage() {
        checkImage = true
        clickTrailers(false)
        progressDetails(false)
        clickMenuHideShow(false)
        imageTrailers(true)
        setImageUrl("${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_BACKDROP}$image")
    }

    override fun setTrailer(data: ObjectDetailsTrailers) {
        hiddenLoading()
        checkImage = false
        clickMenuHideShow(true)
        this.key = data.key
        this.name = data.name
        setImageUrl("${ConstStrings.BASE_URL_IMAGE_YOUTUBE}${data.key}${ConstStrings.SIZE_IMG_YOUTUBE}")
        if (player != null) player!!.cueVideo(data.key)
        else youtubeFragment.initialize(getApiKeyYoutube(), this)

    }

    private fun setImageUrl(url: String) {
        img_trailer_top.setImageUrl(activity!!, url)
    }

    override fun setPausePlayer() {
        if (player != null) player!!.pause()
    }

    override fun showMenuDetailsTop() {
        if (player != null)
            if (!player!!.isPlaying) {
                clickKeyboardHideShow(true)
                clickMenuHideShow(!checkImage)
            }
    }

    override fun hiddenMenuDetailsTop() {
        clickKeyboardHideShow(false)
        clickMenuHideShow(false)
    }

    override fun showPlayer() {
        clickTrailers(false)
        imageTrailers(false)
    }

    override fun hiddenViewsPlayer() {
        titleDetailsHideShow(false)
        clickKeyboardHideShow(false)
        clickMenuHideShow(false)
    }

    override fun showViewsPlayer() {
        titleDetailsHideShow(true)
        clickKeyboardHideShow(true)
        clickMenuHideShow(!checkImage)
    }

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider, p1: YouTubePlayer, p2: Boolean) {
        val style = YouTubePlayer.PlayerStyle.MINIMAL
        p1.setPlayerStyle(style)
        this.player = p1
        p1.setPlayerStateChangeListener(this)
        p1.setPlaybackEventListener(this)
        p1.setShowFullscreenButton(true)
        p1.setManageAudioFocus(true)
        if (!p2) p1.cueVideo(key)
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult) {
        if (p1.isUserRecoverableError) p1.getErrorDialog(activity, 1).show()
    }

    override fun onAdStarted() {}
    override fun onLoading() {}
    override fun onVideoStarted() {
        hiddenViewsPlayer()
    }

    override fun onLoaded(p0: String?) {}
    override fun onVideoEnded() {
        if (getBaseActivity().isMaximized())showViewsPlayer()
    }

    override fun onError(p0: YouTubePlayer.ErrorReason?) {}
    override fun onSeekTo(p0: Int) {}
    override fun onBuffering(p0: Boolean) {}
    override fun onStopped() {}
    override fun onPlaying() {
        hiddenViewsPlayer()
    }
    override fun onPaused() {
        showViewsPlayer()
    }

    override fun onMenuItemClick(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.menu_open_with -> openTrailerYoutube(activity!!, key!!)
            R.id.menu_share_in -> openShareIn(activity!!, "$name - $BASE_URL_BROWSER_YOUTUBE$key")
        }
        return true
    }

    private fun titleDetailsHideShow(b: Boolean) {
        if (b) title_details_top.show() else title_details_top.hide()
    }

    private fun clickKeyboardHideShow(b: Boolean) {
        if (b) click_keyboard_top.show() else click_keyboard_top.hide()
    }

    private fun clickMenuHideShow(b: Boolean) {
        if (b) click_menu_top.show() else click_menu_top.hide()
    }

    private fun clickTrailers(b: Boolean) {
        if (b) click_trailers_top.show() else click_trailers_top.hide()
    }

    private fun progressDetails(b: Boolean) {
        if (b) progress_details_top.show() else progress_details_top.hide()
    }

    private fun imageTrailers(b: Boolean) {
        if (b) img_trailer_top.show() else img_trailer_top.hide()
    }

    private fun fragmentYoutube(b: Boolean) {
        if (b) youtube_fragment_top.show() else youtube_fragment_top.hide()
    }

    private fun failedDetails(b: Boolean) {
        if (b) failed_details_top.show() else failed_details_top.hide()
    }


}
