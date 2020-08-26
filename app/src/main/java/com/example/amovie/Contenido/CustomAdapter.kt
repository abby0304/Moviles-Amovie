package com.example.amovie.Contenido

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amovie.PeliculaContenido
import com.example.amovie.R
import com.squareup.picasso.Picasso

class CustomAdapter (val movieList: ArrayList<Pelicula>, context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var mContext: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.lista_peliculas, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val pelicula: Pelicula = movieList[position]

        holder.titulo.text = pelicula.titulo
        holder.director.text = pelicula.director
        holder.fecha.text = pelicula.fecha

        var imagen_portada = pelicula.imagen_pelicula

        var url = "https://res.cloudinary.com/hciqiholm/image/upload/v1573549900/"+ imagen_portada +".png"
        Picasso.with(mContext)
        .load(url)
        .into(holder.imagen)

        holder.info_peli.setOnClickListener {
            val intent = Intent(mContext, PeliculaContenido::class.java)
            intent.putExtra("id_publicacion", pelicula.id_publicacion)
            intent.putExtra("titulo", pelicula.titulo)
            intent.putExtra("director", pelicula.director)
            intent.putExtra("sinopsis", pelicula.sinopsis)
            intent.putExtra("pais", pelicula.pais)
            intent.putExtra("fecha", pelicula.fecha)
            intent.putExtra("imagen", pelicula.imagen_pelicula)
            intent.putExtra("video", pelicula.video)
            intent.putExtra("maps", pelicula.maps)
            intent.putExtra("fk_usuario", pelicula.fk_usuario)
            intent.putExtra("id_usuario", pelicula.usuario)

            mContext.startActivity(intent)

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val titulo = itemView.findViewById(R.id.tv_Titulo) as TextView
        val director = itemView.findViewById(R.id.tv_Director) as TextView
        val fecha = itemView.findViewById(R.id.tv_Fecha) as TextView
        val info_peli = itemView.findViewById(R.id.btn_informacion) as Button
        val imagen = itemView.findViewById(R.id.iv_portada) as ImageView


    }


}