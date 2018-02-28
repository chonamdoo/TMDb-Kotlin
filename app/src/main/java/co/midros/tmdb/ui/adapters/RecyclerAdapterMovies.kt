package co.midros.tmdb.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.PojoResultsMovie
import co.midros.tmdb.utils.*
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_adapter.view.*
import javax.inject.Inject


class RecyclerAdapterMovies @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterMovies.ViewHolder>() {

    private var list: MutableList<PojoResultsMovie> = mutableListOf()
    private var onItemMovieClickListener: OnItemMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(context.inflateLayout(R.layout.item_adapter, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: PojoResultsMovie) {
        list.add(item)
        notifyItemInserted(itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        val data = list[position]
        holder.itemView.click_adapter.setOnClickListener {
            val backdrop = if (data.backdrop_path != null) data.backdrop_path else ""
            onItemMovieClickListener!!.onItemMovieClicked(data.id, data.title, ConstStrings.MOVIE, backdrop)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PojoResultsMovie) = with(itemView) {
            title_adapter.text = item.title
            date_adapter.text = getReformatDate(item.release_date)
            rating_adapter.text = item.vote_average.toString()

            val preference = DataSharePreference(context)
            val gender = StringBuilder()
            (0 until item.genre_ids.size).asSequence().map { item.genre_ids[it] }
                    .forEach { gender.append(preference.getGenderMovie(it.toString())).append(" ") }
            genero_adapter.text = gender.toString()

            img_poster.setImageUrl(context, "${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.poster_path}")

        }

    }

    interface OnItemMovieClickListener {
        fun onItemMovieClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemMovieClickListener(onItemMovieClickListener: OnItemMovieClickListener) {
        this.onItemMovieClickListener = onItemMovieClickListener
    }

}