package com.example.amovie

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.facebook.CallbackManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var callbackManager: CallbackManager? = null
    private var requestQueue: RequestQueue? = null
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)


        btn_Login.setOnClickListener{

            var email: String = txt_email.text.toString()
            var password: String = txt_pass.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty()) {
               login(email, password)

            }
            else{
                Toast.makeText(this, "No se llenaron todos los campos", Toast.LENGTH_SHORT).show()
            }
        }


        btn_Registro.setOnClickListener{
            val intent= Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }

   private fun login (email: String, pass: String){

       val url ="https://psm-proyecto.herokuapp.com/web/"

      var requestParams = JSONObject()
       requestParams.put("action", "getUsuario")
       requestParams.put("email", email)
       requestParams.put("password", pass)

       mostrarCargando()

       val request = JsonObjectRequest(
           Request.Method.POST,
           url,
           requestParams,{
               response ->
               try {
                   if (response?.getString("email") != null) {
                       Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()

                       val intent = Intent(this, Home::class.java)
                       intent.putExtra("id", response?.getInt("id_usuario"))
                       intent.putExtra("usuario", response?.getString("usuario"))
                       intent.putExtra("email", response?.getString("email"))
                       intent.putExtra("pass", response?.getString("pass"))
                       intent.putExtra("imagen",  response?.getString("imagen_avatar"))
                       intent.putExtra("activo", response?.getInt("activo"))
                       startActivity(intent)

                   }
               }catch (e:Exception){
                   ocultarCargando()
                   Toast.makeText(this, "Contraseña o password incorrecta", Toast.LENGTH_SHORT).show()
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
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


}
