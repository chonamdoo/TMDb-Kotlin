package co.midros.tmdb.ui.activities.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.base.BaseActivity
import co.midros.tmdb.ui.fragments.details.detailsbottom.FragmentDetailsBottom
import co.midros.tmdb.ui.fragments.details.detailstop.FragmentDetailsTop
import co.midros.tmdb.utils.ConstStrings
import com.github.pedrovgs.DraggableListener
import com.miguelcatalan.materialsearchview.MaterialSearchView
import com.pawegio.kandroid.show
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import co.midros.tmdb.utils.KeyboardUtils
import com.pawegio.kandroid.hide
import com.pawegio.kandroid.runDelayedOnUiThread


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, MaterialSearchView.SearchViewListener, MaterialSearchView.OnQueryTextListener, DraggableListener, InterfaceMainView, KeyboardUtils.SoftKeyboardToggleListener {

    @Inject
    lateinit var interactor: InterfaceMainInteractor<InterfaceMainView>

    @Inject lateinit var draggableTopFragment: FragmentDetailsTop
    @Inject lateinit var draggableBottomFragment: FragmentDetailsBottom


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component!!.inject(this)
        interactor.onAttach(this)
        setSupportActionBar(toolbar)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        interactor.setDefaultFragment()
        initializeDrawerLayout()
        initializeDraggable()
        initializeSearchView()
        initializeKeyboardListener()
    }

    private fun initializeKeyboardListener() {
        KeyboardUtils.addKeyboardToggleListener(this, this)
    }

    override fun onToggleSoftKeyboard(isVisible: Boolean) {
        runDelayedOnUiThread(200) {
            if (interactor.getFragmentSearch().isAdded && !draggable_main.isMaximized) {
                if (!draggable_main.isClosedAtLeft && !draggable_main.isClosedAtRight)
                    setMinimizeDraggable()
            }
        }
    }

    override fun onDestroy() {
        interactor.onDetach()
        clearDisposable()
        super.onDestroy()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.nav_search)
        search_toolbar.setMenuItem(item)
        return true
    }

    private fun initializeSearchView() {
        search_toolbar.clearFocus()
        search_toolbar.setOnSearchViewListener(this)
        search_toolbar.setOnQueryTextListener(this)
    }

    private fun initializeDraggable() {

        draggable_main.apply {
            setFragmentManager(supportFragmentManager)
            setTopFragment(draggableTopFragment)
            setBottomFragment(draggableBottomFragment)

            var typedValue = TypedValue()
            resources.getValue(R.dimen.x_scale_factor, typedValue, true)
            val xScaleFactor = typedValue.float
            typedValue = TypedValue()
            resources.getValue(R.dimen.y_scale_factor, typedValue, true)
            val yScaleFactor = typedValue.float

            setXScaleFactor(xScaleFactor)
            setYScaleFactor(yScaleFactor)
            setTopViewHeight(resources.getDimensionPixelSize(R.dimen.top_fragment_height))
            setTopFragmentMarginRight(resources.getDimensionPixelSize(R.dimen.top_fragment_margin))
            setTopFragmentMarginBottom(resources.getDimensionPixelSize(R.dimen.top_fragment_margin))

            isClickToMaximizeEnabled = true
            hide()
        }
        draggable_main.setDraggableListener(this)
        draggable_main.initializeView()
    }

    private fun initializeDrawerLayout() {
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun setCheckedNavigationItem(id: Int) {
        nav_view.menu.findItem(id).isChecked = true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        closeNavigationDrawer()
        when (item.itemId) {
            R.id.nav_movies -> {
                if (!item.isChecked) interactor.setFragmentMovies()
            }
            R.id.nav_tv_shows -> {
                if (!item.isChecked) interactor.setFragmentSeries()
            }
        }
        return true
    }

    override fun setTitleToolbar(title: String) {
        supportActionBar!!.title = title
    }

    override fun closeNavigationDrawer() {
        if (drawer_layout != null) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
    }

    override fun onBackPressed() {
        when {
            draggable_main.isMaximized -> setMinimizeDraggable()
            search_toolbar.isSearchOpen -> search_toolbar.closeSearch()
            drawer_layout.isDrawerOpen(GravityCompat.START) -> closeNavigationDrawer()
            else -> super.onBackPressed()
        }
    }

    override fun isMaximized(): Boolean = draggable_main.isMaximized

    override fun setMinimizeDraggable() {
        draggable_main.minimize()
    }

    override fun setMaximizeDraggable(id: Int, name: String, type: String, image: String) {
        if (draggableTopFragment.getIdDetails() != id) {
            draggableTopFragment.setDetailsTop(id, name, type, image)
            draggableBottomFragment.setDetailsBottom(id, type)
        }
        draggable_main.show()
        draggable_main.maximize()
    }

    override fun setCueVideo(key: String) {
        draggableTopFragment.setCueVideo(key)
    }

    override fun setDrawerLock() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun setDrawerUnLock() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    override fun onMaximized() {
        setDrawerLock()
        hideKeyboard()
        if (draggableTopFragment.isAdded)
            draggableTopFragment.showMenuDetailsTop()
    }

    override fun onMinimized() {
        if (!interactor.getFragmentSearch().isAdded) setDrawerUnLock()
        if (draggableTopFragment.isAdded)
            draggableTopFragment.hiddenMenuDetailsTop()
    }

    override fun onClosedToLeft() {
        if (draggableTopFragment.isAdded)
            draggableTopFragment.setPausePlayer()
    }

    override fun onClosedToRight() {
        if (draggableTopFragment.isAdded)
            draggableTopFragment.setPausePlayer()
    }

    override fun onSearchViewClosed() {
        hideKeyboard()
        runDelayedOnUiThread(500) {
            interactor.closeFragmentSearch(ConstStrings.SEARCH_TAG)
        }
    }

    override fun onSearchViewShown() {
        interactor.setFragmentSearch()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(query: String): Boolean {
        if (interactor.getFragmentSearch().isAdded)
            interactor.getFragmentSearch().setSearch(query)
        return true
    }

}
