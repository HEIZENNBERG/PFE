package com.example.my_pfe

import android.annotation.SuppressLint
import android.database.DataSetObserver
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_pfe.view.AnnonceInfosFragment
import com.example.my_pfe.view.EntrepriseFragment
import com.example.my_pfe.view.EntrepriseInfosFragement
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class EntrepriseAdapter(private val entreprisesList :ArrayList<Entreprise> ) : RecyclerView.Adapter<EntrepriseAdapter.ViewHolderEnt>(),
    Adapter {

    private var filteredList: ArrayList<Entreprise> = ArrayList()

    init {
        filteredList.addAll(entreprisesList)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EntrepriseAdapter.ViewHolderEnt {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entreprise_view, parent, false)
        return EntrepriseAdapter.ViewHolderEnt(view)
    }

    override fun onBindViewHolder(holder: EntrepriseAdapter.ViewHolderEnt, position: Int) {

        val entreprise = filteredList[position]

        Glide.with(holder.itemView.context)
            .load(filteredList[position].url)
            .into(holder.entrepriseLogo)

        holder.entrepriseTitle.text = filteredList[position].nom
        holder.entrepriseDescription.text = filteredList[position].categorie

        holder.itemView.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                val position = holder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val activity = v?.context as AppCompatActivity
                    val itemFragement = EntrepriseInfosFragement()
                    val args = Bundle()
                    args.putParcelable("entreprise", entreprise)
                    itemFragement.arguments = args
                    activity.supportFragmentManager.beginTransaction().replace(R.id.entreprise_frag, itemFragement).addToBackStack(null).commit()
                }
            }
        } )
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }


    fun filter(query: String) {
        filteredList.clear()
        if (query.isEmpty()) {
            filteredList.addAll(entreprisesList)
        } else {
            for (entreprise in entreprisesList) {
                if (entreprise.nom?.toLowerCase(Locale.ROOT)!!.contains(query.toLowerCase(Locale.ROOT)) || entreprise.categorie?.toLowerCase(Locale.ROOT)!!.contains(query.toLowerCase(Locale.ROOT))) {
                    filteredList.add(entreprise)
                }
            }
        }
        notifyDataSetChanged()
    }

    class ViewHolderEnt(ItemView: View) : RecyclerView.ViewHolder(ItemView){
        val entrepriseLogo : ImageView = itemView.findViewById(R.id.entrepriseLogo2)
        val entrepriseTitle : TextView = itemView.findViewById(R.id.entrepriseTitle)
        val entrepriseDescription : TextView = itemView.findViewById(R.id.entrepriseCategorie)
    }

    override fun registerDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun unregisterDataSetObserver(observer: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

}

