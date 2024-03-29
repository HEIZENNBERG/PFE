package com.example.my_pfe.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.*
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EntrepriseFragment : Fragment() {

    lateinit var layoutManager : RecyclerView.LayoutManager
    var adapter : RecyclerView.Adapter<EntrepriseAdapter.ViewHolderEnt> ?= null
    lateinit var aadapter : Adapter

    private lateinit var entrepriseRecyclerView: RecyclerView
    private lateinit var entrepriseList : ArrayList<Entreprise>
    private var db = Firebase.firestore


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

        entrepriseList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("entreprises").get().addOnSuccessListener {
            if(!it.isEmpty){
                for (data in it.documents){
                    val entreprise : Entreprise? = data.toObject(Entreprise::class.java)
                    if (entreprise != null) {
                        entrepriseList.add(entreprise)
                    }
                }
                aadapter = EntrepriseAdapter(entrepriseList)

                adapter = EntrepriseAdapter(entrepriseList)
                entrepriseRecyclerView.adapter = adapter


            }
        }.addOnFailureListener{
            //Toast.makeText(this , it.toString(), Toast.LENGTH_SHORT).show()
        }


        // when the search is used the filter function in adapter called
        val searchView = view.findViewById<SearchView>(R.id.entrepriseSearch)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                (adapter as? EntrepriseAdapter)?.filter(newText)
                return true

            }
        })


    }
}

