package com.example.amovie.Contenido

data class Pelicula(val id_publicacion:String, val titulo:String,  val director:String, val sinopsis:String,
                    val pais:String, val fecha:String, val imagen_pelicula:String, val video:String, val maps:String, val fk_usuario:String, val usuario:String)