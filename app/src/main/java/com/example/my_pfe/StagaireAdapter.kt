package com.example.my_pfe

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class StagaireAdapter(private val demandesList :ArrayList<Demande>, private val clickListener: OnItemClickListener) : RecyclerView.Adapter<StagaireAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.demende_stage, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return demandesList.size
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.stagaireProfile.setImageResource(R.drawable.profile)
        holder.stagaireName.text = demandesList[position].nom+" " + demandesList[position].prenom
        holder.stagairediplome.text = demandesList[position].typeStage
        holder.date.text = SimpleDateFormat("dd/MM/yy HH:mm").format(demandesList[position].date!!)

        val confirm = demandesList[position].confirm
        if (confirm == "accepte" || confirm == "rejecte") {
            holder.accepte.visibility = View.GONE
            holder.cancel.visibility = View.GONE
            holder.confirmation.visibility = View.VISIBLE
            holder.confirmation.text = "Demande: $confirm"
        } else {
            holder.accepte.visibility = View.VISIBLE
            holder.cancel.visibility = View.VISIBLE
            holder.confirmation.visibility = View.GONE
            // Set click listeners for the buttons
            holder.accepte.setOnClickListener {
                // Update the confirm field to "accepte"
                updateDemande(demandesList[position], "accepte")
            }
            holder.cancel.setOnClickListener {
                // Update the confirm field to "rejecte"
                updateDemande(demandesList[position], "rejecte")
            }
        }

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(position)
        }

    }

    class MyViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        var stagaireProfile : ImageView = itemView.findViewById(R.id.stagaireImage)
        var stagaireName : TextView = itemView.findViewById(R.id.stagaireName)
        var stagairediplome : TextView = itemView.findViewById(R.id.stagaireDiplome)
        var date : TextView = itemView.findViewById(R.id.dateDemande)
        var cancel : Button = itemView.findViewById(R.id.cancelDemende)
        var accepte : Button = itemView.findViewById(R.id.accepteDemende)
        var confirmation : TextView = itemView.findViewById(R.id.confirmation)
    }


    private fun updateDemande(demande: Demande, confirm: String) {
        val db = Firebase.firestore
        var entrepriseId = FirebaseAuth.getInstance().currentUser?.uid
        val entrepriseRef = db.collection("entreprises").document(entrepriseId.toString())
        val annonceRef = entrepriseRef.collection("annonces").document(demande.announceID.toString())
        val demandeRef = annonceRef.collection("demandes").document(demande.studentId.toString())
        demandeRef.update("confirm", confirm)
            .addOnSuccessListener {
                val index = demandesList.indexOf(demande)
                demandesList[index].confirm = confirm
                notifyItemChanged(index)
                Log.d(TAG, "Demande updated successfully")

            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error updating demande", e)
            }
    }
}


