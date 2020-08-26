package com.example.amovie

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.amovie.Contenido.AdaptadorFragmentos
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home.*


class Home : AppCompatActivity() {
    private val PICK_IMAGE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        viewPager.adapter = AdaptadorFragmentos(
            supportFragmentManager
        )

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))


        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {

                //botones del layout
                viewPager!!.currentItem = tab.position

                //posicion el layout e inicializar
                if (tab.position == 1) {
                    val ivImgen = findViewById<ImageView>(R.id.ivImagen) as ImageView

                    ivImgen.setOnClickListener {
                        val intent= Intent(this@Home, PeliculaContenido::class.java)
                        startActivity(intent)
                    }
                }
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

        })


    }

}
