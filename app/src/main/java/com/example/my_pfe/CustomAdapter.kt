package com.example.my_pfe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_pfe.view.AnnonceInfosFragment
import com.example.my_pfe.view.EntrepriseInfosFragement
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class CustomAdapter(private val announceList :ArrayList<AnnonceItem>) : RecyclerView.Adapter<CustomAdapter.ViewHolder2>() {

    private var filteredList: ArrayList<AnnonceItem> = ArrayList()

    init {
        filteredList.addAll(announceList)
    }

    // create new views
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder2 {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.announce_view, parent, false)
        return ViewHolder2(view)
    }



    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {

        val annonce = filteredList[position]

        Glide.with(holder.itemView.context)
            .load(filteredList[position].url)
            .into(holder.entrepriseLogo)

        holder.entrepriseName.text = filteredList[position].nom
        holder.adress.text = filteredList[position].adress
        holder.annonceTitle.text = filteredList[position].nomAnnonce


        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val position = holder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val activity = v?.context as AppCompatActivity
                    val itemFragement = AnnonceInfosFragment()
                    val args = Bundle()
                    args.putParcelable("annonce", annonce)
                    itemFragement.arguments = args
                    activity.supportFragmentManager.beginTransaction().replace(R.id.annoncefrag, itemFragement).addToBackStack(null).commit()
                }
            }}
        )
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return filteredList.size
    }



    fun filterAnnounce(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(announceList)
        } else {
            for (annonce in announceList) {
                if (annonce.departement?.toLowerCase(Locale.ROOT)!!.contains(query.toLowerCase(Locale.ROOT)) || annonce.nom?.toLowerCase(Locale.ROOT)!!.contains(query.toLowerCase(Locale.ROOT)) || annonce.nomAnnonce?.toLowerCase(
                        Locale.ROOT)!!.contains(query.toLowerCase(Locale.ROOT))) {
                    filteredList.add(annonce)
                }
            }
        }
        notifyDataSetChanged()
    }

    // Holds the views for adding it to image and text
    class ViewHolder2(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val entrepriseLogo: ImageView = itemView.findViewById(R.id.entrepriseLogo)
        val annonceTitle: TextView = itemView.findViewById(R.id.annonceTitle)
        val entrepriseName : TextView = itemView.findViewById(R.id.entrepriseName)
        val adress : TextView = itemView.findViewById(R.id.entrepriseAdress)
    }


}
