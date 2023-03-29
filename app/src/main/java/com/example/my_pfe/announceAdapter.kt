package com.example.my_pfe

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.view.AnnonceInfosFragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class announceAdapter(
     private var announcelist: ArrayList<AnnonceItem>,
     mainActivityEntreprise: MainActivityEntreprise,
     private val itemClickListener: OnItemClickListener
 ) : RecyclerView.Adapter<announceAdapter.MyViewHolder>() {
    private var filteredList: ArrayList<AnnonceItem> = ArrayList()

    init {
        filteredList.addAll(announcelist)
    }

    private val builder: AlertDialog.Builder = AlertDialog.Builder(mainActivityEntreprise)

     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): announceAdapter.MyViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.entreprise_annonce, parent, false)

         return announceAdapter.MyViewHolder(view)
     }

     @SuppressLint("SimpleDateFormat")
     override fun onBindViewHolder(holder: announceAdapter.MyViewHolder, position: Int) {
         val announce = filteredList[position]

         holder.announceTitle.text  = filteredList[position].nomAnnonce
         holder.announceDepartment.text = filteredList[position].departement
         holder.dateAnnonce.text = SimpleDateFormat("dd/MM/yy HH:mm").format(filteredList[position].date)
         holder.delete.setOnClickListener {

             builder.setTitle("delete!!")
                 .setMessage("you really want to delete this announce ?")
                 .setCancelable(true)
                 .setPositiveButton("yes"){dialogInterface, it->
                     val db = FirebaseFirestore.getInstance()
                     db.collection("entreprises")
                         .document(announce.entrepriseId!!)
                         .collection("annonces")
                         .document(announce.id!!)
                         .delete()
                         .addOnSuccessListener {
                             // Delete subcollection documents
                             db.collection("entreprises")
                                 .document(announce.entrepriseId!!)
                                 .collection("annonces")
                                 .document(announce.id!!)
                                 .collection("demandes")
                                 .get()
                                 .addOnSuccessListener { querySnapshot ->
                                     for (document in querySnapshot) {
                                         document.reference.delete()
                                     }
                                 }
                                 .addOnFailureListener { exception ->
                                     // Handle any errors
                                 }
                             filteredList.removeAt(position)
                             notifyDataSetChanged()
                         }
                         .addOnFailureListener { exception ->
                             // Handle any errors
                         }
                 }
                 .setNegativeButton("no"){dialogInterface, it->
                     dialogInterface.cancel()
                 }.show()



         }

         holder.itemView.setOnClickListener {
             itemClickListener.onItemClick(position)
         }
     }


    override fun getItemCount(): Int {
        return filteredList.size
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val announceTitle : TextView = itemView.findViewById(R.id.entre_annonceName)
         val announceDepartment : TextView = itemView.findViewById(R.id.announceDepartment)
         val dateAnnonce : TextView = itemView.findViewById(R.id.dateAnnonce)
         val delete : ImageButton = itemView.findViewById(R.id.deleteEntrepriseEnnounce)
     }
    fun filter(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(announcelist)
        } else {
            for (annonce in announcelist) {
                if (annonce.nomAnnonce?.toLowerCase(Locale.ROOT)!!.contains(query.toLowerCase(Locale.ROOT)) ){
                    filteredList.add(annonce)
                }
            }
        }
        notifyDataSetChanged()
    }
}