package co.midros.tmdb.ui.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectsDetailsImages
import co.midros.tmdb.utils.ConstStrings
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_pager_images.view.*

/**
 * Created by luis rafael on 12/02/18.
 */
class ViewPagerImages(private var context: Context, private var list: MutableList<ObjectsDetailsImages>) : PagerAdapter() {

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == (`object` as LinearLayout)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pager_images, container, false)
        view.img_pager_images.setImageUrl(context, "${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_W780}${list[position].file_path}")
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    fun ImageView.setImageUrl(context: Context, url: String) {
        Glide.with(context).load(url)
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .apply(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(this)
    }


}
