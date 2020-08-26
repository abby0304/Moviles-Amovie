package com.example.amovie.Database

data class cUsuario(var id: Long?, var correo:String?, var username:String?, var contra:String?, var imagen:String?) {
    companion object{
        const val TABLE_USUARIO: String= "USUARIO"
        const val ID:String = "IDUsuario"
        const val correo:String ="correo"
        const val username:String="username"
        const val contra:String="contra"
        const val imagen:String="imagen"

    }
}