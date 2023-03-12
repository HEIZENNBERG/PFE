package com.example.my_pfe

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.my_pfe.databinding.ActivityMainEntrepriseBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityEntreprise : AppCompatActivity(), OnItemClickListener {

    lateinit var mainBinding: ActivityMainEntrepriseBinding
    lateinit var announceRecyclerView: RecyclerView
    private lateinit var announceList : ArrayList<AnnonceItem>
    private lateinit var adapter : announceAdapter
    private var isListnersEnabeld = true
    lateinit var layoutManager : RecyclerView.LayoutManager
    private var db = Firebase.firestore
    private var entrepriseId = FirebaseAuth.getInstance().currentUser?.uid
    private var entrepriseRef = db.collection("entreprises").document(entrepriseId!!)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainEntrepriseBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        announceRecyclerView = view.findViewById(R.id.announceRecyclerView)
        layoutManager = LinearLayoutManager(this)
        announceRecyclerView.layoutManager = layoutManager

        announceList = arrayListOf()

        //mainBinding.addAnn.visibility = VISIBLE

        db = FirebaseFirestore.getInstance()
        db.collection("entreprises").document(entrepriseId!!).collection("annonces").get().addOnSuccessListener {
            if(!it.isEmpty){
                for (data in it.documents){
                    val announce : AnnonceItem? = data.toObject(AnnonceItem::class.java)
                    if (announce != null) {
                        announceList.add(announce)
                    }
                }
                adapter = announceAdapter(announceList, this, this)
                announceRecyclerView.adapter = adapter

            }
        }.addOnFailureListener{
            //Toast.makeText(this , it.toString(), Toast.LENGTH_SHORT).show()
        }



            // Add any necessary code to initialize the views and components
            mainBinding.entrepriseProfile.setOnClickListener {
                if(isListnersEnabeld)
                {
                    val intent = Intent(this, EntrepriseProfile::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            mainBinding.entrepriseMainName.setOnClickListener {
                if(isListnersEnabeld)
                {
                    val intent = Intent(this, EntrepriseProfile::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        mainBinding.addAnn.setOnClickListener {
            if(isListnersEnabeld)
            {
                val intent = Intent(this, AddEntrepriseAnnounce::class.java)
                startActivity(intent)
                finish()
            }

        }
        displayHeaderInfo(view)
    }

    fun enableListeners() {
        isListnersEnabeld = true
    }

    fun displayHeaderInfo(view : View){


        entrepriseRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    val nom = document.getString("nom")
                    val url = document.getString("url")
                    val imageUrl = url

                    val imageView = view.findViewById<ImageView>(R.id.entrepriseProfile)

                    Glide.with(this)
                        .load(imageUrl)
                        .into(imageView)

                    val nomTextView =  view?.findViewById<TextView>(R.id.entrepriseMainName)
                    nomTextView?.text = nom
                }
            }
    }

    override fun onItemClick(position: Int) {
        val intent = Intent(this, EntrepriseAnnounceInfos::class.java)
        intent.putExtra("name", announceList[position].nomAnnonce)
        intent.putExtra("id", announceList[position].id)
        intent.putExtra("entrepriseId", announceList[position].entrepriseId)
        startActivity(intent)
    }

}


