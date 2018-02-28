package co.midros.tmdb.objects

import java.io.Serializable

/**
 * Created by luis rafael on 31/01/18.
 */
data class ObjectsPerson(
        var biography: String,
        var birthday: String,
        var homepage: String,
        var name: String,
        var place_of_birth: String,
        var profile_path: String
) : Serializable