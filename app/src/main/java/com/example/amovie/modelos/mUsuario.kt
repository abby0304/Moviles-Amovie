package com.example.amovie.modelos

import android.graphics.Bitmap

class mUsuario {

    var id: Int = 0
    var correo: String? = null
    var username: String? = null
    var contra: String? = null
    var imagePath: String? = null
    var bitmap: Bitmap? = null

    constructor() {}

    constructor(correo: String, username: String, contra: String, bitmap: Bitmap) {
        this.correo = correo
        this.username = username
        this.contra = contra
        this.bitmap = bitmap
    }

    constructor(correo: String, username: String, contra: String, imagePath: String) {
        this.correo = correo
        this.username = username
        this.contra = contra
        this.imagePath = imagePath
    }

    constructor(id: Int, correo: String, username: String, contra: String, imagePath: String) {
        this.id = id
        this.correo = correo
        this.username = username
        this.contra = contra
        this.imagePath = imagePath
    }

}