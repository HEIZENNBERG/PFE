package com.example.my_pfe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.my_pfe.AnnonceItem
import com.example.my_pfe.Entreprise
import com.example.my_pfe.R


class AnnonceInfosFragment : Fragment() {

    lateinit var returnBtn : ImageButton
    private lateinit var annonce: AnnonceItem


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        annonce = arguments?.getParcelable("annonce") ?: AnnonceItem()
        // Get the URI of the image from the Entreprise object
        val imageUrl = annonce.url

        val imageView = view.findViewById<ImageView>(R.id.entrepriseLogo)

// Load the image using Glide and set it to the ImageView in your layout

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        // Now you can use the entreprise object to display the additional information in your UI
        // For example, you can update the text views like this:
        view.findViewById<TextView>(R.id.annonceTitle).text = annonce.nomAnnonce
        view.findViewById<TextView>(R.id.entrepriseName2).text = annonce.nom
        view.findViewById<TextView>(R.id.annonceDescription).text = annonce.descriptionAnnonce
        view.findViewById<TextView>(R.id.entrepriseDescrition).text = annonce.email



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_annonce_infos, container, false)


        returnBtn = view.findViewById(R.id.goBack)

        returnBtn.setOnClickListener {
            // Get the FragmentManager
            val fragmentManager = parentFragmentManager

            // Pop the current fragment and return to the previous one
            fragmentManager.popBackStack()
        }


    return view
    }


}