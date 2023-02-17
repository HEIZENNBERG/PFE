package com.example.my_pfe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.EntrepriseAdapter
import com.example.my_pfe.NotificationAdapter
import com.example.my_pfe.R

class EntrepriseFragment : Fragment() {

    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var adapter : RecyclerView.Adapter<EntrepriseAdapter.ViewHolderEnt>
    lateinit var entrepriseRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entreprise, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        entrepriseRecyclerView = view.findViewById(R.id.entrepriseRecyclerView)
        layoutManager = LinearLayoutManager(activity)
        entrepriseRecyclerView.layoutManager = layoutManager
        // set the custom adapter to the RecyclerView
        adapter = EntrepriseAdapter()
        entrepriseRecyclerView.adapter = adapter

    }

}