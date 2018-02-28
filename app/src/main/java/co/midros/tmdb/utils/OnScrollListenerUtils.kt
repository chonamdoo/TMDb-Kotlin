package co.midros.tmdb.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.pawegio.kandroid.i

/**
 * Created by luis rafael on 24/02/18.
 */
class OnScrollListenerUtils(private val func: () -> Unit, private val lManager: GridLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = lManager.itemCount
            firstVisibleItem = lManager.findFirstVisibleItemPosition()

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached
                i("OnScrollListenerUtils", "End reached")
                func()
                loading = true
            }
        }
    }
}