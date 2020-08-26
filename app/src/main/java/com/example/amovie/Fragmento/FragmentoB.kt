package com.example.amovie.Fragmento

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.amovie.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.home_user.*


class FragmentoB : Fragment()  {

    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        rootView = inflater.inflate(R.layout.home_user, container, false)

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvUserName.text = activity?.intent?.extras?.getString("usuario")
        tvEmail.text = activity?.intent?.extras?.getString("email")

        var imagen_user = activity?.intent?.extras?.getString("imagen")
        var url = "https://res.cloudinary.com/hciqiholm/image/upload/c_fill,ar_1:1,g_auto,r_max/v1573549900/"+ imagen_user.toString() +".png"
        Picasso.with(context)
            .load(url)
            .into(im_user)


    }

}