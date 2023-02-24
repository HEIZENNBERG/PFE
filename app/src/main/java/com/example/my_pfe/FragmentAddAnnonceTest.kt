package com.example.my_pfe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class FragmentAddAnnonceTest : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_annonce_test, container, false)
        val returnToHome = view.findViewById<Button>(R.id.goBackToHome)

        returnToHome.setOnClickListener {
            // Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

            // Pop the current fragment and return to the previous one
            fragmentManager.popBackStack()
        }

        return view
    }

      override fun onDetach() {
        super.onDetach()
        (activity as MainActivityEntreprise).enableListeners()
    }

}