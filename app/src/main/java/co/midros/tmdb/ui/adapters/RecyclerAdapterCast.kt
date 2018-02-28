package co.midros.tmdb.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import co.midros.tmdb.R
import co.midros.tmdb.objects.ObjectsDetailsCast
import co.midros.tmdb.utils.ConstStrings
import co.midros.tmdb.utils.setImageUrl
import com.pawegio.kandroid.inflateLayout
import kotlinx.android.synthetic.main.item_cast.view.*
import javax.inject.Inject

/**
 * Created by luis rafael on 11/02/18.
 */
class RecyclerAdapterCast @Inject constructor(private var context: Context) : RecyclerView.Adapter<RecyclerAdapterCast.ViewHolder>() {

    private var list: MutableList<ObjectsDetailsCast> = mutableListOf()
    private var onItemCastClickListener: OnItemCastClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) =
            ViewHolder(context.inflateLayout(R.layout.item_cast, parent, false))

    override fun getItemCount(): Int = list.size

    fun addItem(item: ObjectsDetailsCast) {
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
            val profile = if (data.profile_path != null) data.profile_path else ""
            onItemCastClickListener!!.onItemCastClicked(data.id, data.name, ConstStrings.PERSON, profile)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ObjectsDetailsCast) = with(itemView) {
            name_cast.text = item.name
            character_cast.text = item.character
            img_cast.setImageUrl(context, "${ConstStrings.BASE_URL_IMAGE}${ConstStrings.SIZE_IMAGE_POSTER}${item.profile_path}")
        }
    }

    interface OnItemCastClickListener {
        fun onItemCastClicked(id: Int, title: String, type: String, image: String)
    }

    fun setOnItemCastClickListener(onItemCastClickListener: OnItemCastClickListener) {
        this.onItemCastClickListener = onItemCastClickListener
    }

}