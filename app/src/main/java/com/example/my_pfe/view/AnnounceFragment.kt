package com.example.my_pfe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.my_pfe.AnnonceItem
import com.example.my_pfe.CustomAdapter
import com.example.my_pfe.R

class AnnounceFragment : Fragment() {

    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var adapter : RecyclerView.Adapter<CustomAdapter.ViewHolder2>
    lateinit var annoncesRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_announce, container, false)
        annoncesRecyclerView = view.findViewById(R.id.annoncesRecyclerView)
        adapter = CustomAdapter()
        annoncesRecyclerView.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        annoncesRecyclerView = view.findViewById(R.id.annoncesRecyclerView)
        layoutManager = LinearLayoutManager(activity)
        annoncesRecyclerView.layoutManager = layoutManager
            // set the custom adapter to the RecyclerView
        adapter = CustomAdapter()
        annoncesRecyclerView.adapter = adapter

    }

}