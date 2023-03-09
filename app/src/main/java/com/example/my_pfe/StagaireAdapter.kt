package com.example.my_pfe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StagaireAdapter : RecyclerView.Adapter<StagaireAdapter.MyViewHolder>() {

    val stgImages = arrayOf(R.drawable.profile, R.drawable.profile,R.drawable.profile,R.drawable.profile)
    val stgNames = arrayOf("ISSAM ERRAFIY", "YASSIN MESKAOUI", "OUSSAMA NAJEM", "SAAD SSS")
    val stgDes = arrayOf("DUT diplome", "DUT diplome", "DUT diplome", "DUT diplome")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.demende_stage, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return stgNames.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.stagaireProfile.setImageResource(stgImages[position])
        holder.stagaireName.text = stgNames[position]
        holder.stagairediplome.text = stgDes[position]
    }

    class MyViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var stagaireProfile : ImageView = itemView.findViewById(R.id.stagaireImage)
        var stagaireName : TextView = itemView.findViewById(R.id.stagaireName)
        var stagairediplome : TextView = itemView.findViewById(R.id.stagaireDiplome)
    }
}