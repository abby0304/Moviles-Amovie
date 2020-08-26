package com.example.amovie.Contenido

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amovie.R
import com.squareup.picasso.Picasso

class CustomComentario (val comentarList: ArrayList<Comentarios>, context: Context) : RecyclerView.Adapter<CustomComentario.ViewHolder>(){
    var mContext: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomComentario.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.comentarios, parent, false)
        return CustomComentario.ViewHolder(v)
    }

    override fun getItemCount(): Int {
       return comentarList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comentar: Comentarios = comentarList[position]
        holder.tusuario.text = comentar.email
        holder.tomentario.text = comentar.comentario

        var imagen_portada = comentar.imagen_avatar

        var url = "https://res.cloudinary.com/hciqiholm/image/upload/c_fill,ar_1:1,g_auto,r_max/v1573549900/"+ imagen_portada +".png"
        Picasso.with(mContext)
            .load(url)
            .into(holder.tmagen)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tusuario = itemView.findViewById(R.id.txt_usuario) as TextView
        val tomentario = itemView.findViewById(R.id.txt_comentario) as TextView
        val tmagen = itemView.findViewById(R.id.iv_usuario) as ImageView
    }

}