package com.example.my_pfe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.my_pfe.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnnounceFragment : Fragment() {

    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var adapter : RecyclerView.Adapter<CustomAdapter.ViewHolder2>
    lateinit var annoncesRecyclerView : RecyclerView
    private lateinit var announceList : ArrayList<AnnonceItem>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_announce, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        annoncesRecyclerView = view.findViewById(R.id.annoncesRecyclerView)
        layoutManager = LinearLayoutManager(activity)
        annoncesRecyclerView.layoutManager = layoutManager



        announceList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        db.collection("entreprises").get().addOnSuccessListener { entreprises ->
            if (!entreprises.isEmpty) {
                for (entreprise in entreprises.documents) {
                    db.collection("entreprises").document(entreprise.id).collection("annonces").get().addOnSuccessListener { annonces ->
                        if (!annonces.isEmpty) {
                            for (annonce in annonces.documents) {
                                val annonceItem : AnnonceItem? = annonce.toObject(AnnonceItem::class.java)
                                if (annonceItem != null) {
                                    announceList.add(annonceItem)
                                }
                            }
                        }
                        adapter = CustomAdapter(announceList)
                        annoncesRecyclerView.adapter = adapter
                    }
                }
            }
        }.addOnFailureListener {
            // Handle error
        }

        adapter = CustomAdapter(announceList)


    val searchView = view.findViewById<SearchView>(R.id.searchView)


    searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

        override fun onQueryTextSubmit(query: String): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String): Boolean {

            (adapter as CustomAdapter).filterAnnounce(newText)
            return true


        }
    })
    }
}