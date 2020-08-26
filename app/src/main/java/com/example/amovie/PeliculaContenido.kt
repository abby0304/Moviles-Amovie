package com.example.amovie

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.amovie.Contenido.Comentarios
import com.example.amovie.Contenido.CustomComentario
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pelicula_contenido.*
import org.json.JSONArray
import org.json.JSONObject

@Suppress("DEPRECATION")
class PeliculaContenido : AppCompatActivity() {

    private var uri: Uri? = null
    private var mediacontroller: MediaController? = null
    private lateinit var progressDialog: ProgressDialog
    val url = "https://psm-proyecto.herokuapp.com/web/"
    val comenta = ArrayList<Comentarios>()
    val adapter = CustomComentario(comenta, this)

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelicula_contenido)

        val id_u_p = intent.getStringExtra("fk_usuario")
        val id_p = intent.getStringExtra("id_publicacion")
        val id_user = intent.getStringExtra("id_usuario")
        val titulo = intent.getStringExtra("titulo")
        val director = intent.getStringExtra("director")
        val sinopsis = intent.getStringExtra("sinopsis")
        val pais = intent.getStringExtra("pais")
        val fecha = intent.getStringExtra("fecha")
        val imagen = intent.getStringExtra("imagen")
        val video = intent.getStringExtra("video")
        val maps = intent.getStringExtra("maps")

        txt_Titulo.text = titulo
        txt_fecha.text = fecha
        txt_director.text = "Por: " + director
        txt_sinopsis.text = "Sinopsis: " + sinopsis
        txt_Pais.text = "Pais: " + pais

        var url_imagen = "https://res.cloudinary.com/hciqiholm/image/upload/v1573549900/"+ imagen +".png"
        Picasso.with(this)
            .load(url_imagen)
            .into(iv_Portada)


        getComentarios( id_p, 0)

        //RecyclerView

        val recyclerView = findViewById(R.id.rv_Comentarios) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        recyclerView.adapter = adapter

        btn_agregar.setOnClickListener {
            comenta.clear()

            val comentario = tv_comentario.text.toString()

            addComentarios(comentario, id_user, id_p)

            val textView: TextView = findViewById(R.id.tv_comentario) as TextView

            textView.text = "Escribe tu comentario"
        }

    }

    private fun getComentarios(id_p:String, activo:Int){

        var requestParams = JSONObject()
        requestParams.put("action", "getComentarios")
        requestParams.put("id_publicacion", id_p)

        mostrarCargando()

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestParams, { response ->
                try {
                    val jsonArray = JSONArray("${response["comentar"]}")
                    for (i in 0 until jsonArray.length()) {

                        comenta.add(Comentarios(
                            jsonArray.getJSONObject(i).getString("comentario"),
                            jsonArray.getJSONObject(i).getString("imagen_avatar"),
                            jsonArray.getJSONObject(i).getString("email")))
                    }

                    ocultarCargando()

                } catch (e: Exception) {
                    Toast.makeText(this, "query incorrecto", Toast.LENGTH_SHORT).show()
                }

            },
            { error ->
                ocultarCargando()
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()

            }
        )

        //Peticion al servidor
        val queue= Volley.newRequestQueue(this)
        queue.add(request)

    }


    private fun addComentarios(comentario:String, id_usuario:String, id_publi:String){
        var requestParams = JSONObject()
        requestParams.put("action", "addComentario")
        requestParams.put("comentario", comentario)
        requestParams.put("fk_usuario", id_usuario)
        requestParams.put("id_publicacion", id_publi)

        mostrarCargando()

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestParams, { response ->
                try {
                    val jsonArray = JSONArray("${response["comentar"]}")
                    for (i in 0 until jsonArray.length()) {

                        comenta.add(Comentarios(
                            jsonArray.getJSONObject(i).getString("comentario"),
                            jsonArray.getJSONObject(i).getString("imagen_avatar"),
                            jsonArray.getJSONObject(i).getString("email")))
                    }

                    adapter.notifyDataSetChanged()

                    ocultarCargando()

                } catch (e: Exception) {
                    Toast.makeText(this, "query incorrecto", Toast.LENGTH_SHORT).show()
                }

            },
            { error ->
                ocultarCargando()
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()

            }
        )

        //Peticion al servidor
        val queue= Volley.newRequestQueue(this)
        queue.add(request)
    }

    private fun mostrarCargando(){
        if(!::progressDialog.isInitialized)
            progressDialog = ProgressDialog(this)

        progressDialog.setMessage("Cargando...")

        progressDialog.isIndeterminate =true

        progressDialog.setCancelable(false)

        progressDialog.show()

    }

    private fun ocultarCargando(){

        progressDialog.dismiss()
    }

}
