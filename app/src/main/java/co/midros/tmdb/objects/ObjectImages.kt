package co.midros.tmdb.objects

import java.io.Serializable

/**
 * Created by luis rafael on 26/01/18.
 */
data class ObjectImages(var id: Int, var backdrops: MutableList<ObjectsDetailsImages>) : Serializable

data class ObjectsDetailsImages(var file_path: String, var vote_count: Int) : Serializable