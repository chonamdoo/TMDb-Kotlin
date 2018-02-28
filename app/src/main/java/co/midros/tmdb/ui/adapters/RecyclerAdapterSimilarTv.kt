package co.midros.tmdb.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.PojoResultsTv
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.setImageUrl
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_adapter_multi.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 29/01/18.
 */
class RecyclerAdapterSimilarTv @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterSimilarTv.ViewHolder>() {

    private var list: MutableList<PojoResultsTv> = mutableListOf()
    private var onItemTvClickListener: OnItemTvClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_adapter_multi, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: PojoResultsTv) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.setOnClickListener {
            val backdrop = if (data.backdrop_path != null) data.backdrop_path else ""
            onItemTvClickListener!!.onItemTvClicked(data.id, data.name, ConstStrings.TV, backdrop)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PojoResultsTv) = with(itemView) {
            img_multi.setImageUrl(context, "${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.poster_path}")
        }
    }

    interface OnItemTvClickListener {
        fun onItemTvClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemTvClickListener(onItemTvClickListener: OnItemTvClickListener) {
        this.onItemTvClickListener = onItemTvClickListener
    }
}