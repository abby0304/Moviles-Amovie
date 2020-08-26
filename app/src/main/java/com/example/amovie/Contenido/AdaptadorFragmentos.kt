@file:Suppress("DEPRECATION")

package com.example.amovie.Contenido

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.amovie.Fragmento.FragmentoA
import com.example.amovie.Fragmento.FragmentoB
import com.example.amovie.Fragmento.FragmentoC


class AdaptadorFragmentos(fm: FragmentManager
): FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when( position ) {
            0->FragmentoA()
            1->FragmentoB()
            2->FragmentoC()
            else-> FragmentoA()
        }
    }

    override fun getCount() = 3


}