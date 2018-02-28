package co.midros.tmdb.ui.activities.main

import co.midros.tmdb.ui.activities.base.InterfaceView

/**
 * Created by luis rafael on 20/02/18.
 */
interface InterfaceMainView : InterfaceView {

    fun setCheckedNavigationItem(id: Int)
    fun closeNavigationDrawer()
    fun setTitleToolbar(title: String)
    fun setDrawerUnLock()
    fun setDrawerLock()
}