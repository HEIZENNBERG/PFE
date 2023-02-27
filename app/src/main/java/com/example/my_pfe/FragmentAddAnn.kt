package com.example.my_pfe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.my_pfe.R.layout.fragment_add_annonce2

class FragmentAddAnn : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_annonce2, container, false)

        val returnBtn = view.findViewById<Button>(R.id.backHM)


        returnBtn.setOnClickListener {
            // Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

            // Pop the current fragment and return to the previous one
            fragmentManager.popBackStack()
        }



        return view
    }



}