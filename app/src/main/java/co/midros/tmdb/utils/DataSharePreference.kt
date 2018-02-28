package co.midros.tmdb.utils

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import androidx.content.edit
import com.pawegio.kandroid.e
import javax.inject.Inject

/**
 * Created by luis rafael on 22/01/18.
 */
class DataSharePreference @Inject constructor(private var context: Context) {

    private lateinit var genderMovie: SharedPreferences
    private lateinit var genderTv:SharedPreferences

    fun setGenderMovie(id: String, gender: String) {
        genderMovie = context.getSharedPreferences(ConstStrings.MOVIE, Context.MODE_PRIVATE)
        genderMovie.edit { putString(id,gender) }
    }

    fun getGenderMovie(id: String): String {
        genderMovie = context.getSharedPreferences(ConstStrings.MOVIE, Context.MODE_PRIVATE)
        return genderMovie.getString(id, "")
    }

    fun setGenderTvShow(id: String, gender: String) {
        genderTv = context.getSharedPreferences(ConstStrings.TV, Context.MODE_PRIVATE)
        genderTv.edit { putString(id,gender) }
    }

    fun getGenderTvShow(id: String): String {
        genderTv = context.getSharedPreferences(ConstStrings.TV, Context.MODE_PRIVATE)
        return genderTv.getString(id, "")
    }
}