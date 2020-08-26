package com.example.amovie.Fragmento

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.amovie.Contenido.CustomAdapter
import com.example.amovie.Contenido.Pelicula
import com.example.amovie.R
import org.json.JSONArray
import org.json.JSONObject

@Suppress("DEPRECATION")
class FragmentoA : Fragment() {

    lateinit var rootView: View
    private lateinit var progressDialog: ProgressDialog
    val pelicula = ArrayList<Pelicula>()

    @SuppressLint("WrongConstant")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.home_list, container, false)

        if (pelicula.size == 0) {

            val url = "https://psm-proyecto.herokuapp.com/web/"

            var requestParams = JSONObject()
            requestParams.put("action", "getPeliculas")

            mostrarCargando()

            val request = JsonObjectRequest(
                Request.Method.POST,
                url,
                requestParams, { response ->
                    try {
                        val jsonArray = JSONArray("${response["publi"]}")
                        for (i in 0 until jsonArray.length()) {
                            pelicula.add(
                                Pelicula(
                                    jsonArray.getJSONObject(i).getString("id_publicacion"),
                                    jsonArray.getJSONObject(i).getString("titulo"),
                                    jsonArray.getJSONObject(i).getString("director"),
                                    jsonArray.getJSONObject(i).getString("sinopsis"),
                                    jsonArray.getJSONObject(i).getString("pais"),
                                    jsonArray.getJSONObject(i).getString("fecha"),
                                    jsonArray.getJSONObject(i).getString("imagen_pelicula"),
                                    jsonArray.getJSONObject(i).getString("video"),
                                    jsonArray.getJSONObject(i).getString("maps"),
                                    jsonArray.getJSONObject(i).getString("fk_usuario"),
                                    activity?.intent?.extras?.getInt("id").toString()
                                )
                            )
                        }
                        ocultarCargando()

                    } catch (e: Exception) {
                        Toast.makeText(this.activity, "query incorrecto", Toast.LENGTH_SHORT).show()
                    }

                },
                { error ->
                    ocultarCargando()
                    Toast.makeText(this.activity, error.toString(), Toast.LENGTH_SHORT).show()

                }
            )

            //Peticion al servidor
            val queue= Volley.newRequestQueue(this.activity)
            queue.add(request)
        }


        //DISEÑO LAYOUT
        val recycle = rootView.findViewById(R.id.rv_list) as RecyclerView

        recycle.layoutManager = LinearLayoutManager(this.activity, LinearLayout.VERTICAL, false)

        val adapter = CustomAdapter(pelicula, this.requireContext())

        recycle.adapter = adapter

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun mostrarCargando(){
        if(!::progressDialog.isInitialized)
            progressDialog = ProgressDialog(this.activity)

        progressDialog.setMessage("Cargando...")

        progressDialog.isIndeterminate =true

        progressDialog.setCancelable(false)

        progressDialog.show()

    }

    private fun ocultarCargando(){

        progressDialog.dismiss()
    }



}