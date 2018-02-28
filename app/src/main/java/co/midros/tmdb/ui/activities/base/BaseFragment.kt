package co.midros.tmdb.ui.activities.base

import android.content.Context
import android.support.v4.app.Fragment
import co.midros.tmdb.di.components.ActivityComponent
import co.midros.tmdb.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 19/02/18.
 */
abstract class BaseFragment : Fragment(), InterfaceView {

    private var activity: BaseActivity? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.activity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    fun getActivityComponent(): ActivityComponent? {
        return if (activity != null) {
            activity!!.getActivityComponent()
        } else null
    }

    override fun addDisposable(disposable: Disposable) {
        if (activity != null) activity!!.addDisposable(disposable)
    }

    override fun clearDisposable() {
        if (activity != null) activity!!.clearDisposable()
    }

    override fun showMessage(message: String) {
        if (activity != null) activity!!.showMessage(message)
    }

    override fun hideKeyboard() {
        if (activity != null) activity!!.hideKeyboard()
    }

    fun getBaseActivity(): BaseActivity = activity!!

    interface Callback {
        fun setMinimizeDraggable()
        fun isMaximized() : Boolean
        fun setMaximizeDraggable(id: Int, name: String, type: String, image: String)
        fun setCueVideo(key: String)
    }


}
