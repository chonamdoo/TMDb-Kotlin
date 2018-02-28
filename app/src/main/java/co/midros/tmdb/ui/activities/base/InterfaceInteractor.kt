package co.midros.tmdb.ui.activities.base

import android.content.Context
import android.support.v4.app.FragmentManager

/**
 * Created by luis rafael on 20/02/18.
 */
interface InterfaceInteractor<V : InterfaceView> {

    fun onAttach(view: V)

    fun onDetach()

    fun getInterfaceView(): V

    fun getContext(): Context

    fun getSupportFragmentManager(): FragmentManager

}