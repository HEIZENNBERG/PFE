package com.example.my_pfe

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.NonDisposableHandle.parent
import java.text.SimpleDateFormat

class NotificationAdapter(private val demandesList :ArrayList<Demande>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder3>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ViewHolder3 {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_view, parent, false)
        return ViewHolder3(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder3, position: Int) {

        val annonceId = demandesList[position].announceID.toString()
        getAnnonceById(annonceId) { annonce ->
            if (annonce != null) {
                Glide.with(holder.itemView.context)
                    .load(annonce.url)
                    .into(holder.entrepriselogo)
                holder.entreprisename.text = annonce.nomAnnonce
                holder.annoncename.text = annonce.nom
                holder.confirmation.text = demandesList[position].confirm
                holder.demandedate.text = SimpleDateFormat("dd/MM/yy HH:mm").format(demandesList[position].date)

            } else {
                // Annonce not found or error occurred
            }
        }


        if (demandesList[position].confirm == "pas de reponce") {
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.grey))
        }
        else if (demandesList[position].confirm == "rejecte") {
            holder.layout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.redo))
        }

    }

    override fun getItemCount(): Int {
        return demandesList.size
    }

    inner class ViewHolder3(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val entrepriselogo : ImageView = itemView.findViewById(R.id.entrepriseLogoNotif)
        val entreprisename : TextView = itemView.findViewById(R.id.entrepriseTitleNotif)
        val annoncename : TextView = itemView.findViewById(R.id.annonceNotif)
        val demandedate : TextView = itemView.findViewById(R.id.dateDemande)
        val confirmation : TextView = itemView.findViewById(R.id.confirmation)
        val delete : ImageButton = itemView.findViewById(R.id.imageButton)
        val layout : ConstraintLayout = itemView.findViewById(R.id.myConstraintLayout)

        init {
            delete.setOnClickListener {
                val annonceId = demandesList[adapterPosition].announceID.toString()

                var builder = AlertDialog.Builder(it.context)

                    delete.setOnClickListener {
                        val annonceId = demandesList[adapterPosition].announceID.toString()


                        builder.setTitle("Log out !!")
                            .setMessage("you sure want to delete demand ?")
                            .setCancelable(true)
                            .setPositiveButton("yes"){dialogInterface, it->
                                //delete the notification
                            }
                            .setNegativeButton("no"){dialogInterface, it->
                                dialogInterface.cancel()
                            }.show()

                        true
                    }


                    getAnnonceById(annonceId) { annonce ->
                    if (annonce != null) {
                        val demandeId = FirebaseAuth.getInstance().currentUser?.uid.toString()
                        val db = Firebase.firestore

                        // Delete the demande from Firestore
                        db.collection("entreprises")
                            .document(annonce.entrepriseId.toString())
                            .collection("annonces")
                            .document(annonceId)
                            .collection("demandes")
                            .document(demandeId)
                            .delete()
                            .addOnSuccessListener {
                                // Remove the demande from the list and notify the adapter
                                demandesList.removeAt(adapterPosition)
                                notifyDataSetChanged()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error deleting document", e)
                            }
                    } else {
                        // Annonce not found or error occurred
                    }
                }
            }
        }

    }

    fun getAnnonceById(annonceid: String, onComplete: (annonce: AnnonceItem?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("entreprises")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    db.collection("entreprises")
                        .document(document.id)
                        .collection("annonces")
                        .document(annonceid)
                        .get()
                        .addOnSuccessListener { documentSnapshot ->
                            if (documentSnapshot.exists()) {
                                val annonce = documentSnapshot.toObject(AnnonceItem::class.java)
                                onComplete(annonce)
                            }
                        }
                }
                onComplete(null)
            }
    }
}