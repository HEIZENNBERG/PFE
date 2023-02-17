package com.example.my_pfe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.view.AnnonceInfosFragment

class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.ViewHolder2>() {

    // get this data from firebase
    var annonces_title = arrayOf("annonce1", "annonce2", "annonce3", "annonce4", "annonce5","annonce6")
    var entreprise_name = arrayOf("entreprise1", "entreprise2", "entreprise3", "entreprise4", "entreprise5","entreprise6")
    var entreprise_logo = arrayOf(R.drawable.entreprise, R.drawable.entreprise, R.drawable.entreprise, R.drawable.entreprise, R.drawable.entreprise, R.drawable.entreprise)
    var adress = arrayOf("khrebga Larb3a", "rabat", "jadida", "casabanca", "tanger", "essaouira")

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.announce_view, parent, false)

        return ViewHolder2(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.entrepriseLogo.setImageResource(entreprise_logo[position])
        holder.annonceTitle.text = annonces_title[position]
        holder.entrepriseName.text = entreprise_name[position]
        holder.adress.text = adress[position]

        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                val activity=v!!.context as AppCompatActivity
                val itemFragement = AnnonceInfosFragment()
                activity.supportFragmentManager.beginTransaction().replace(R.id.annoncefrag, itemFragement).addToBackStack(null).commit()

            }
        } )
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return annonces_title.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder2(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val entrepriseLogo: ImageView = itemView.findViewById(R.id.entrepriseLogo)
        val annonceTitle: TextView = itemView.findViewById(R.id.annonceTitle)
        val entrepriseName : TextView = itemView.findViewById(R.id.entrepriseName)
        val adress : TextView = itemView.findViewById(R.id.entrepriseAdress)
    }
}
