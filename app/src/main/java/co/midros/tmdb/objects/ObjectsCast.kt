package co.midros.tmdb.objects

import java.io.Serializable

/**
 * Created by luis rafael on 11/02/18.
 */
data class ObjectsCast(var cast: MutableList<ObjectsDetailsCast>) : Serializable

data class ObjectsDetailsCast(
        var character: String,
        var id: Int,
        var name: String,
        var profile_path: String
) : Serializable