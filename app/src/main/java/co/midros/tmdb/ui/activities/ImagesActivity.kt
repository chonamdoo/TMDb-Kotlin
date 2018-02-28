package co.midros.tmdb.ui.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.view.WindowManager
import co.midros.tmdb.R
import co.midros.tmdb.ui.adapters.ViewPagerImages
import co.midros.tmdb.objects.ObjectImages
import co.midros.tmdb.utils.pageTransformer
import kotlinx.android.synthetic.main.activity_view_image.*

/**
 * Created by luis rafael on 12/02/18.
 */
class ImagesActivity : FragmentActivity(), ViewPager.OnPageChangeListener {

    private var data: ObjectImages? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_image)
        data = intent.extras.getSerializable("data") as ObjectImages
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initializePagerAdapter()
        setViews()
    }

    override fun onResume() {
        super.onResume()
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
    }

    @SuppressLint("SetTextI18n")
    private fun setViews() {
        txt_total_images.text = "/${data!!.backdrops.size.toString()}"
        close_view_image.setOnClickListener { finish() }
    }

    private fun initializePagerAdapter() {
        val adapterImages = ViewPagerImages(this, data!!.backdrops)
        view_adapter_images.apply {
            setPageTransformer(true, pageTransformer())
            adapter = adapterImages
        }
        adapterImages.notifyDataSetChanged()
        view_adapter_images.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        txt_image_actual.text = (position + 1).toString()
    }

}