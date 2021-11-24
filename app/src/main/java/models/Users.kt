package models

import java.io.Serializable

class Users : Serializable {
    var idToken: String? = null
    var name: String? = null
    var imageUrl: String? = null
    var email: String? = null


    constructor(idToken: String?, name: String?, imageUrl: String?, email: String?) {
        this.idToken = idToken
        this.name = name
        this.imageUrl = imageUrl
        this.email = email
    }

    constructor()

}