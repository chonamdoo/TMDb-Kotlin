package co.midros.tmdb.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectsDetailsImages
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.setImageUrl
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_images.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 27/01/18.
 */
class RecyclerAdapterImages @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterImages.ViewHolder>() {

    private var list: MutableList<ObjectsDetailsImages> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_images, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: ObjectsDetailsImages) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(list[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ObjectsDetailsImages) = with(itemView) {
            img_images.setImageUrl(context, "${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_BACKDROP}" + item.file_path)
            voto_images.text = item.vote_count.toString()
        }
    }
}