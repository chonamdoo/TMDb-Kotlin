package co.midros.tmdb.di.modules

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import co.midros.tmdb.ui.activities.main.InteractorMain
import co.midros.tmdb.ui.activities.main.InterfaceMainInteractor
import co.midros.tmdb.ui.activities.main.InterfaceMainView
import co.midros.tmdb.di.PerActivity
import co.midros.tmdb.ui.fragments.details.detailsbottom.FragmentDetailsBottom
import co.midros.tmdb.ui.fragments.details.detailstop.FragmentDetailsTop
import co.midros.tmdb.utils.ConstStrings
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by luis rafael on 19/02/18.
 */
@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context = activity.applicationContext

    @Provides
    fun provideActivity(): AppCompatActivity = activity

    @Provides
    fun provideSupportFragmentManager(): FragmentManager = activity.supportFragmentManager

    @Provides
    fun provideFragmentDetailsTop(): FragmentDetailsTop = FragmentDetailsTop()

    @Provides
    fun provideFragmentDetailsBottom(): FragmentDetailsBottom = FragmentDetailsBottom()

    @Provides
    @PerActivity
    fun provideInterfaceMainInteractor(interactor: InteractorMain<InterfaceMainView>): InterfaceMainInteractor<InterfaceMainView> = interactor

    @Provides
    fun provideYouTubePlayerSupportFragment(): YouTubePlayerSupportFragment = YouTubePlayerSupportFragment.newInstance()

    @Provides
    @Named(ConstStrings.DEFAULT)
    fun provideGridLayoutManager(context: Context): GridLayoutManager = GridLayoutManager(context, 1)

    @Provides
    @Named(ConstStrings.HORIZONTAL)
    fun provideGridLayoutManagerHorizontal(context: Context): GridLayoutManager = GridLayoutManager(context, 1, GridLayoutManager.HORIZONTAL, false)

    @Provides
    @Named(ConstStrings.COUNT3)
    fun provideGridLayoutManagerCount3(context: Context): GridLayoutManager = GridLayoutManager(context, 3)

}