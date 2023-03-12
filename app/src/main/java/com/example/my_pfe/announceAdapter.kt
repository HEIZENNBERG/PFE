package com.example.my_pfe

import android.annotation.SuppressLint
import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.view.AnnonceInfosFragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class announceAdapter(
     private var announcelist: ArrayList<AnnonceItem>,
     mainActivityEntreprise: MainActivityEntreprise,
     private val itemClickListener: OnItemClickListener
 ) : RecyclerView.Adapter<announceAdapter.MyViewHolder>() {

     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): announceAdapter.MyViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.entreprise_annonce, parent, false)
         return announceAdapter.MyViewHolder(view)
     }

     @SuppressLint("SimpleDateFormat")
     override fun onBindViewHolder(holder: announceAdapter.MyViewHolder, position: Int) {
         val announce = announcelist[position]

         holder.announceTitle.text  = announcelist[position].nomAnnonce
         holder.announceDepartment.text = announcelist[position].departement
         holder.dateAnnonce.text = SimpleDateFormat("dd/MM/yy HH:mm").format(announcelist[position].date)
         holder.delete.setOnClickListener {
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
                     announcelist.removeAt(position)
                     notifyDataSetChanged()
                 }
                 .addOnFailureListener { exception ->
                     // Handle any errors
                 }
         }

         holder.itemView.setOnClickListener {
             itemClickListener.onItemClick(position)
         }
     }


    override fun getItemCount(): Int {
        return announcelist.size
    }

     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val announceTitle : TextView = itemView.findViewById(R.id.entre_annonceName)
         val announceDepartment : TextView = itemView.findViewById(R.id.announceDepartment)
         val dateAnnonce : TextView = itemView.findViewById(R.id.dateAnnonce)
         val delete : ImageButton = itemView.findViewById(R.id.deleteEntrepriseEnnounce)
     }

}