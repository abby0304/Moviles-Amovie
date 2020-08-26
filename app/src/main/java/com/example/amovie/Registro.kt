package com.example.amovie

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registro.*
import org.jetbrains.anko.toast
import org.json.JSONObject
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class Registro : AppCompatActivity() {

    private val PICK_IMAGE = 100
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)



        btn_aceptar.setOnClickListener {
            val email: String = txt_Email.text.toString()
            val contra: String = txt_Contra.text.toString()
            val user: String = txt_NombreUsuario.text.toString()

            if(email.isNotEmpty() && contra.isNotEmpty() && user.isNotEmpty()) {
                agregarUsuario(email, contra, user)
            }else
            {
               this.toast("No se llenaron todos los campos")

            }
        }

        //btn_examinar.setOnClickListener { val intent = Intent(this, MainActivity) }
        foto_gallery.setOnClickListener { openGallery() }
    }

    private fun agregarUsuario(email: String, pass: String, user: String){

        val bitmap = (foto_gallery.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        var imageString = android.util.Base64.encodeToString(image, Base64.DEFAULT)


        val url ="https://psm-proyecto.herokuapp.com/web/"

        var requestParams = JSONObject()
        requestParams.put("action", "addUsuario")
        requestParams.put("usuario", user)
        requestParams.put("email", email)
        requestParams.put("password", pass)
        requestParams.put("imagen_base64", imageString)

        mostrarCargando()

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            requestParams,{
                    response ->
                try {
                    if (response?.getString("inserto") != null) {
                        Toast.makeText(this, response?.getString("inserto"), Toast.LENGTH_SHORT).show()

                        val intent= Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    }
                }catch (e:Exception){
                    Toast.makeText(this, "Ese usuario ya no esta disponible", Toast.LENGTH_SHORT).show()
                }

            },
            {
                    error ->
                ocultarCargando()
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show()

            }
        )

        //Peticion al servidor
        val queue= Volley.newRequestQueue(this)
        queue.add(request)

    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, PICK_IMAGE)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            //Mostrar imagen
            foto_gallery.setImageURI(data?.data)

            //val imageUri = data?.getData()
            //val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)

        }
    }

    }
