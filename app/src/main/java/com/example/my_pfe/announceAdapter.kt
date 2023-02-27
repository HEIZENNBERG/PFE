package com.example.my_pfe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

 class announceAdapter(private var announcelist : ArrayList<AnnonceItem>) : RecyclerView.Adapter<announceAdapter.MyViewHolder>() {



     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): announceAdapter.MyViewHolder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.entreprise_annonce, parent, false)
         return announceAdapter.MyViewHolder(view)
     }

     override fun onBindViewHolder(holder: announceAdapter.MyViewHolder, position: Int) {
         val announce = announcelist[position]

         holder.announceTitle.text  = announcelist[position].nomAnnonce
         holder.announceDepartment.text = announcelist[position].departement
     }


    override fun getItemCount(): Int {
        return announcelist.size
    }


     class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val announceTitle : TextView = itemView.findViewById(R.id.entre_annonceName)
         val announceDepartment : TextView = itemView.findViewById(R.id.announceDepartment)
     }

}