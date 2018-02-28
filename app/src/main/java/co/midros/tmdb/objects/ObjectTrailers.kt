package co.midros.tmdb.objects

import java.io.Serializable

/**
 * Created by luis rafael on 26/01/18.
 */
class ObjectTrailers(
        var id: Int,
        var results: MutableList<ObjectDetailsTrailers>
) : Serializable

class ObjectDetailsTrailers(
        var key: String,
        var name: String,
        var size: String
) : Serializable
