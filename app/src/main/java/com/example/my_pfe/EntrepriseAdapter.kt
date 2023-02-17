package com.example.my_pfe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.view.AnnonceInfosFragment
import com.example.my_pfe.view.EntrepriseFragment
import com.example.my_pfe.view.EntrepriseInfosFragement
import org.w3c.dom.Text

class EntrepriseAdapter() : RecyclerView.Adapter<EntrepriseAdapter.ViewHolderEnt>() {

    var entrepriseLogos = arrayOf(R.drawable.entreprise, R.drawable.entreprise,R.drawable.entreprise,R.drawable.entreprise)
    var entrepriseTitles = arrayOf("entreprise1", "entreprise2", "entreprise3", "entreprise4")
    var entrepriseDescriptions = arrayOf("description1", "description2","description3","description4")


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntrepriseAdapter.ViewHolderEnt {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entreprise_view, parent, false)
        return EntrepriseAdapter.ViewHolderEnt(view)
    }

    override fun onBindViewHolder(holder: EntrepriseAdapter.ViewHolderEnt, position: Int) {
        holder.entrepriseLogo.setImageResource(entrepriseLogos[position])
        holder.entrepriseTitle.text = entrepriseTitles[position]
        holder.entrepriseDescription.text = entrepriseDescriptions[position]

        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {

                val activity=v!!.context as AppCompatActivity
                val itemFragement = EntrepriseInfosFragement()
                activity.supportFragmentManager.beginTransaction().replace(R.id.entreprise_frag, itemFragement).addToBackStack(null).commit()

            }
        } )
    }

    override fun getItemCount(): Int {
        return entrepriseTitles.size
    }

    class ViewHolderEnt(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val entrepriseLogo : ImageView = itemView.findViewById(R.id.entrepriseLogo2)
        val entrepriseTitle : TextView = itemView.findViewById(R.id.entrepriseTitle)
        val entrepriseDescription : TextView = itemView.findViewById(R.id.entrepriseDescrption)
    }

}