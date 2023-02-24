package com.example.my_pfe

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.view.AnnonceInfosFragment
import com.example.my_pfe.view.AnnounceFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var fullNameTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val drawarLayout = findViewById<DrawerLayout>(R.id.drawarLayout)
        val imgMenu = findViewById<ImageView>(R.id.imgMenu)

        val navView = findViewById<NavigationView>(R.id.navDawar)
        navView.itemIconTintList = null
        imgMenu.setOnClickListener {
            drawarLayout.openDrawer(GravityCompat.START)
        }
        val navController = Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupWithNavController(navView,navController)

        val textTitle = findViewById<TextView>(R.id.title)
        navController
            .addOnDestinationChangedListener { controller, destination, arguments ->
                textTitle.text = destination.label
            }
        val headerView = navView.getHeaderView(0)
        fullNameTextView = headerView.findViewById(R.id.fullname)

        displayStudentInfo()
    }

    private fun replaceFragement(announceFragment: AnnounceFragment) {
        val fragementManager = supportFragmentManager
        val fragementTransaction = fragementManager.beginTransaction()
        fragementTransaction.replace(R.id.annoncefrag, announceFragment)
        fragementTransaction.commit()
    }


    private fun displayStudentInfo() {
        val db = Firebase.firestore
        val studentId = FirebaseAuth.getInstance().currentUser?.uid
        val studentRef = db.collection("students").document(studentId!!)

        studentRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    val nom = document.getString("nom")
                    val prenom = document.getString("prenom")


                    // get references to TextViews



                    // set text values of TextViews
                    fullNameTextView.text = nom +" "+prenom

                } else {
                    // document not found
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                // handle errors
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }
}