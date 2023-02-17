package com.example.my_pfe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotificationAdapter() : RecyclerView.Adapter<NotificationAdapter.ViewHolder3>() {

    var entrepriselogo = arrayOf(R.drawable.entreprise, R.drawable.entreprise, R.drawable.entreprise, R.drawable.entreprise)
    var entreprisename = arrayOf("entreprise1", "entreprise2", "entreprise3", "entreprise4")
    var annoncename = arrayOf("annonce1", "annonce2", "annonce3", "annonce4")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder3 {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_view, parent, false)
        return ViewHolder3(view)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder3, position: Int) {
        holder.entrepriselogo.setImageResource(entrepriselogo[position])
        holder.entreprisename.text = entreprisename[position]
        holder.annoncename.text = annoncename[position]
    }

    override fun getItemCount(): Int {
        return entreprisename.size
    }

    class ViewHolder3(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val entrepriselogo : ImageView = itemView.findViewById(R.id.entrepriseLogoNotif)
        val entreprisename : TextView = itemView.findViewById(R.id.entrepriseTitleNotif)
        val annoncename : TextView = itemView.findViewById(R.id.annonceNotif)
    }
}