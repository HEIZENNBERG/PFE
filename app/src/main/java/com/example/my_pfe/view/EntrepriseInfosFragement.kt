package com.example.my_pfe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.example.my_pfe.R


class EntrepriseInfosFragement : Fragment() {

    lateinit var returnBtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entreprise_infos_fragement, container, false)

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