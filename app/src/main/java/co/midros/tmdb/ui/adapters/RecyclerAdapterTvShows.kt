package co.midros.tmdb.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.PojoResultsTv
import co.midros.tmdb.utils.*
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_adapter.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 22/01/18.
 */
class RecyclerAdapterTvShows @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterTvShows.ViewHolder>() {

    private var list: MutableList<PojoResultsTv> = mutableListOf()
    private var onItemTvClickListener: OnItemTvClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_adapter, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: PojoResultsTv) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.click_adapter.setOnClickListener {
            val backdrop = if (data.backdrop_path != null) data.backdrop_path else ""
            onItemTvClickListener!!.onItemTvClicked(data.id, data.name, ConstStrings.TV, backdrop)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: PojoResultsTv) = with(itemView) {
            title_adapter.text = item.name
            date_adapter.text = getReformatDate(item.first_air_date)
            rating_adapter.text = item.vote_average.toString()

            val preference = DataSharePreference(context)
            val gender = StringBuilder()
            (0 until item.genre_ids.size).asSequence().map { item.genre_ids[it] }
                    .forEach { gender.append(preference.getGenderTvShow(it.toString())).append(" ") }
            genero_adapter.text = gender.toString()

            img_poster.setImageUrl(context, "${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.poster_path}")
        }
    }

    interface OnItemTvClickListener {
        fun onItemTvClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemMovieClickListener(onItemTvClickListener: OnItemTvClickListener) {
        this.onItemTvClickListener = onItemTvClickListener
    }
}