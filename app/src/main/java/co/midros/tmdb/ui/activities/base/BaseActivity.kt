package co.midros.tmdb.ui.activities.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import co.midros.tmdb.app.TmdbApp
import co.midros.tmdb.di.components.ActivityComponent
import co.midros.tmdb.di.components.DaggerActivityComponent
import co.midros.tmdb.di.modules.ActivityModule
import co.midros.tmdb.utils.KeyboardUtils
import co.midros.tmdb.utils.schedulers.SchedulerProvider
import com.pawegio.kandroid.longToast
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by luis rafael on 19/02/18.
 */
abstract class BaseActivity : AppCompatActivity(), InterfaceView, BaseFragment.Callback {

    companion object {
        @JvmStatic var component: ActivityComponent? = null

        @JvmStatic var compositeDisposable: CompositeDisposable?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component = DaggerActivityComponent.builder().activityModule(ActivityModule(this))
                .appsComponent(TmdbApp.component).build()
        compositeDisposable = CompositeDisposable()
    }

    fun getActivityComponent(): ActivityComponent? {
        return if (component != null) component else null
    }

    override fun hideKeyboard() {
        KeyboardUtils.hiddenKeyboard(this)
    }

    override fun showMessage(message: String) {
        longToast(message)
    }

    override fun addDisposable(disposable: Disposable){
        compositeDisposable!!.add(disposable)
    }

    override fun clearDisposable() {
        compositeDisposable!!.clear()
    }

    override fun setMinimizeDraggable() {}
    override fun setMaximizeDraggable(id: Int, name: String, type: String, image: String) {}
    override fun setCueVideo(key: String) {}
    override fun isMaximized(): Boolean = true

}