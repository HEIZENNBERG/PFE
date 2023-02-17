package com.example.my_pfe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //replaceFragement(AnnounceFragment())

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


    }

    private fun replaceFragement(announceFragment: AnnounceFragment) {
        val fragementManager = supportFragmentManager
        val fragementTransaction = fragementManager.beginTransaction()
        fragementTransaction.replace(R.id.annoncefrag, announceFragment)
        fragementTransaction.commit()
    }
}