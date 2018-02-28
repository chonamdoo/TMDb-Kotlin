package co.midros.tmdb.ui.activities.base

import co.midros.tmdb.utils.schedulers.BaseSchedulerProvider
import io.reactivex.disposables.Disposable


/**
 * Created by luis rafael on 20/02/18.
 */
interface InterfaceView  {

    fun hideKeyboard()

    fun showMessage(message: String)

    fun addDisposable(disposable: Disposable)

    fun clearDisposable()

}