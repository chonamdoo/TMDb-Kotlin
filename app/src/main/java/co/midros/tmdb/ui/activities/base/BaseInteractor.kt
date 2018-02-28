package co.midros.tmdb.ui.activities.base

import android.content.Context
import android.support.v4.app.FragmentManager
import javax.inject.Inject

/**
 * Created by luis rafael on 20/02/18.
 */
open class BaseInteractor<V : InterfaceView> @Inject constructor(private var supportFragment: FragmentManager, private var context: Context) : InterfaceInteractor<V> {

    private var view: V? = null

    override fun onAttach(view: V) {
        this.view = view
    }

    override fun onDetach() {
        view = null
    }

    override fun getInterfaceView(): V = view!!

    override fun getContext(): Context = context

    override fun getSupportFragmentManager(): FragmentManager = supportFragment

}