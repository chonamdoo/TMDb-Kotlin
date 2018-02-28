package co.midros.tmdb.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.net.toUri
import co.midros.tmdb.BuildConfig
import co.midros.tmdb.R
import co.midros.tmdb.ui.activities.main.MainActivity
import co.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_APP_YOUTUBE
import co.midros.tmdb.utils.ConstStrings.Companion.BASE_URL_BROWSER_YOUTUBE
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.pawegio.kandroid.animListener
import com.pawegio.kandroid.e
import com.pawegio.kandroid.loadAnimation
import com.pawegio.kandroid.startActivity
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by luis rafael on 26/01/18.
 */
fun getApiKeyTmdb(): String = BuildConfig.API_KEY_TMDB

fun getApiKeyYoutube(): String = BuildConfig.API_KEY_YOUTUBE

fun getLocale(): String = Locale.getDefault().toString().replace("_", "-")
fun getCountry(): String = Locale.getDefault().country

fun getFormattedDollars(doublePayment: Int): String {
    val n = NumberFormat.getCurrencyInstance(Locale.US)
    return n.format(doublePayment)
}

fun addLinks(textView: TextView, text: String, link: String) {
    val pattern = Pattern.compile(text)
    android.text.util.Linkify.addLinks(textView, pattern, link, { _, _, _ -> true }, { _, _ -> "" })
}

fun e(t:Throwable){
    e(ConstStrings.TAG, t.message.toString())
}

fun ImageView.setImageUrl(context: Context, url: String) {
    Glide.with(context).load(url)
            .apply(RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(context.getDrawable(R.drawable.ic_placeholder_error))
                    .placeholder(context.getDrawable(R.drawable.ic_placeholder))
            )
            .into(this)
}

fun getReformatDate(dateInString: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return try {
        val date = parser.parse(dateInString)
        formatter.format(date)
    } catch (e: ParseException) {
        dateInString
    }
}

fun openActivityMain(activity: Activity) {
    activity.startActivity<MainActivity>()
    activity.finish()
    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}

fun openTrailerYoutube(activity: Activity, key: String) {
    val intentAppYoutube = Intent(Intent.ACTION_VIEW, "$BASE_URL_APP_YOUTUBE$key".toUri())
    val intentBrowser = Intent(Intent.ACTION_VIEW, "$BASE_URL_BROWSER_YOUTUBE$key".toUri())
    try {
        activity.startActivity(intentAppYoutube)
    } catch (e: ActivityNotFoundException) {
        activity.startActivity(intentBrowser)
    }
}

fun openShareIn(activity: Activity, text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.share_in))
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
}

fun fadeZoomTransitionActivity(activity: Activity, view: View) {
    val animation = activity.loadAnimation(R.anim.fade_in)
    animation.duration = 1000
    animation.animListener {
        onAnimationEnd {
            view.startAnimation(activity.loadAnimation(R.anim.zoom_repeat))
        }
    }
    view.startAnimation(animation)
}

fun animatedView(t: Techniques, v: View, l: Long) {
    YoYo.with(t).duration(l).pivot(YoYo.CENTER_PIVOT, YoYo.CENTER_PIVOT).playOn(v)
}

@SuppressLint("RestrictedApi")
fun removeShiftMode(view: BottomNavigationView) {
    val menuView = view.getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            item.setChecked(item.itemData.isChecked)
        }
    } catch (ex: NoSuchFieldException) {
        e("BottomNav", "Unable to get shift mode field " + ex)
    } catch (ex: IllegalAccessException) {
        e("BottomNav", "Unable to change value of shift mode " + ex)
    }

}

fun pageTransformer(): ViewPager.PageTransformer = ViewPager.PageTransformer { page, position ->
    page.translationX = if (position < 0.0f) 0.0f else (-page.width).toFloat() * position
}
