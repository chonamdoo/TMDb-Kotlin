package co.midros.tmdb.objects

import java.io.Serializable

/**
 * Created by luis rafael on 26/01/18.
 */
data class ObjectGenders(val genres: MutableList<ObjectListGenders>) : Serializable

data class ObjectListGenders(val id: Int, val name: String) : Serializable