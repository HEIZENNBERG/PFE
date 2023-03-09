package com.example.my_pfe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class AnnonceEntrepriseInfos : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {

        }*/

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_annonce_entreprise_infos, container, false)

        val returnHome = view.findViewById<ImageButton>(R.id.goBackFromAnnonce)
        val annonce_name = view.findViewById<TextView>(R.id.annonceName)


        returnHome.setOnClickListener {
            // Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

            // Pop the current fragment and return to the previous one
            fragmentManager.popBackStack()
        }

        return view
    }


}