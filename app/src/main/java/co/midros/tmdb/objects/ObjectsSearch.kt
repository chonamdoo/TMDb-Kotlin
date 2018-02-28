package co.midros.tmdb.objects

import com.google.gson.JsonArray
import java.io.Serializable

/**
 * Created by luis rafael on 30/01/18.
 */
data class ObjectsSearch(
        var page: Int,
        var total_pages: Int,
        var results: JsonArray
) : Serializable