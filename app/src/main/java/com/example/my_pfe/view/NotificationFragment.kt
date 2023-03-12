package com.example.my_pfe.view

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NotificationFragment : Fragment() {
    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var adapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder3>
    lateinit var notificationRecyclerView : RecyclerView
    private lateinit var demandeList : ArrayList<Demande>
    private var db = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        layoutManager = LinearLayoutManager(activity)
        notificationRecyclerView.layoutManager = layoutManager
        // set the custom adapter to the RecyclerView

        demandeList = arrayListOf()
        db = FirebaseFirestore.getInstance()

        val currentStudentId = FirebaseAuth.getInstance().currentUser?.uid
        if (currentStudentId != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("entreprises")
                .get()
                .addOnSuccessListener { querySnapshotEntreprises ->
                    for (documentEntreprise in querySnapshotEntreprises) {
                        db.collection("entreprises")
                            .document(documentEntreprise.id)
                            .collection("annonces")
                            .get()
                            .addOnSuccessListener { querySnapshotAnnonces ->
                                for (documentAnnonce in querySnapshotAnnonces) {
                                    db.collection("entreprises")
                                        .document(documentEntreprise.id)
                                        .collection("annonces")
                                        .document(documentAnnonce.id)
                                        .collection("demandes")
                                        .whereEqualTo("studentId", currentStudentId)
                                        .get()
                                        .addOnSuccessListener { querySnapshotDemandes ->
                                            for (documentDemande in querySnapshotDemandes) {
                                                val demande = documentDemande.toObject(Demande::class.java)
                                                demandeList.add(demande)
                                            }
                                            adapter = NotificationAdapter(demandeList)
                                            notificationRecyclerView.adapter = adapter
                                            adapter.notifyDataSetChanged()
                                        }
                                        .addOnFailureListener { e ->
                                            Log.e(TAG, "Error getting demandes for student $currentStudentId and annonce ${documentAnnonce.id}", e)
                                        }
                                }
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Error getting annonces for entreprise ${documentEntreprise.id}", e)
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error getting entreprises", e)
                }
        }

    }
}