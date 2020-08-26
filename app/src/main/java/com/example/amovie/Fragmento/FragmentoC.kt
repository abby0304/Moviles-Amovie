package com.example.amovie.Fragmento

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.amovie.R
import kotlinx.android.synthetic.main.home_agregar.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*

@Suppress("DEPRECATION")
class FragmentoC: Fragment() {
    lateinit var rootView: View
    private val PICK_IMAGE = 100

    private lateinit var progressDialog: ProgressDialog

    var year : Int?=null
    var month : Int?=null
    var day : Int?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.home_agregar, container, false)

        val c = Calendar.getInstance()
        year = c.get(Calendar.YEAR)
        month = c.get(Calendar.MONTH)
        day = c.get(Calendar.DAY_OF_MONTH)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ivImagenPelicula.setOnClickListener { openGallery() }

        btn_agregar.setOnClickListener {


            var fecha: String = txt_fecha_m.text.toString()
            var titulo: String = tv_Titulo.text.toString()
            var director: String = tv_Director.text.toString()
            var sinopsis: String = tv_Sinopsis.text.toString()
            var pais: String = tv_Pais.text.toString()

            if(fecha.isNotEmpty() && titulo.isNotEmpty() && director.isNotEmpty() && sinopsis.isNotEmpty() && pais.isNotEmpty()) {

                agregarMovie(fecha, titulo, director, sinopsis, pais)


            }else
            {
                Toast.makeText(this.activity, "No se llenaron todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        btn_calendario.setOnClickListener {
            val dpd = DatePickerDialog(this.context!!, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
               txt_fecha_m.setText(""+ mDay + "/" + mMonth + "/"+ mYear)
            }, year!!, month!!, day!!)

            dpd.show()
        }
    }

    private fun agregarMovie (fecha: String, titulo: String, director:String, sinopsis:String, pais:String) {

        var id = activity?.intent?.extras?.getInt("id")

        val bitmap = (ivImagenPelicula.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        var imageString = android.util.Base64.encodeToString(image, Base64.DEFAULT)

        val url ="https://psm-proyecto.herokuapp.com/web/"

        var requestParams = JSONObject()
        requestParams.put("action", "addPelicula")
        requestParams.put("fecha", fecha)
        requestParams.put("titulo", titulo)
        requestParams.put("director", director)
        requestParams.put("sinopsis", sinopsis)
        requestParams.put("pais", pais)
        requestParams.put("imagen_base64", imageString)
        requestParams.put("fk_usuario", id.toString())

        mostrarCargando()

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestParams,{
                    response ->
                try {
                    if (response?.getString("inserto") != null) {
                        Toast.makeText(this.activity, response?.getString("inserto"), Toast.LENGTH_SHORT).show()
                        ocultarCargando()

                    }
                }catch (e:Exception){
                    Toast.makeText(this.activity, "Ese usuario ya no esta disponible", Toast.LENGTH_SHORT).show()
                }

            },
            {
                    error ->
                ocultarCargando()
                Toast.makeText(this.activity, error.toString(), Toast.LENGTH_SHORT).show()

            }
        )

        //Peticion al servidor
        val queue= Volley.newRequestQueue(this.activity)
        queue.add(request)



    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == AppCompatActivity.RESULT_OK && requestCode == PICK_IMAGE){
            //Mostrar imagen
            ivImagenPelicula.setImageURI(data?.data)

        }
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