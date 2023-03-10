package com.example.my_pfe.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.my_pfe.AnnonceItem
import com.example.my_pfe.Entreprise
import com.example.my_pfe.R
import com.google.android.material.textfield.TextInputLayout
import java.io.IOException


class AnnonceInfosFragment : Fragment() {

    lateinit var returnBtn : ImageButton
    private lateinit var annonce: AnnonceItem
    lateinit var typeStage : TextInputLayout
    lateinit var importcv : Button
    lateinit var submit : Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        annonce = arguments?.getParcelable("annonce") ?: AnnonceItem()
        // Get the URI of the image from the Entreprise object
        val imageUrl = annonce.url

        val imageView = view.findViewById<ImageView>(R.id.entrepriseLogo)

        // Create an array of items to display
        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        // Create an ArrayAdapter to populate the dropdown menu
        //val stages = resources.getStringArray(R.array.type_stage)
        val adapter = ArrayAdapter(requireContext(), R.layout.list_categorie, items)
        adapter.setDropDownViewResource(R.layout.list_categorie)

        // Create an ArrayAdapter to display the items
        //val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        // Set the adapter for the ListView
       // list_view.adapter = adapter

// Load the image using Glide and set it to the ImageView in your layout

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        // Now you can use the entreprise object to display the additional information in your UI
        // For example, you can update the text views like this:
        view.findViewById<TextView>(R.id.annonceTitle).text = annonce.nomAnnonce
        view.findViewById<TextView>(R.id.entrepriseName2).text = annonce.nom
        view.findViewById<TextView>(R.id.annonceDescription).text = annonce.descriptionAnnonce
        view.findViewById<TextView>(R.id.entrepriseDescrition).text = annonce.email


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_annonce_infos, container, false)

        returnBtn = view.findViewById(R.id.goBack)
        typeStage = view.findViewById(R.id.typeStageEditText)
        importcv = view.findViewById(R.id.importCV)
        submit = view.findViewById(R.id.submitDemende)


        val selectFileLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            { uri ->
                if (uri != null) {
                    try {
                        val inputStream = requireActivity().contentResolver.openInputStream(uri)
                        // Read the contents of the file using the input stream
                        inputStream?.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        )
       // importcv = view.findViewById<Button>(R.id.selectFileButton)
        importcv.setOnClickListener { selectFileLauncher.launch("application/pdf") } // you can set specific mime types like "application/pdf"


        // Create an ArrayAdapter to populate the dropdown menu
       /* val stages = resources.getStringArray(R.array.type_stage)
        val adapter = ArrayAdapter(this.requireContext(), R.layout.list_categorie, stages)
        adapter.setDropDownViewResource(R.layout.list_categorie)*/

        // Get the dropdown menu view and set the adapter
       /* val demendeStage : TextInputLayout = view.findViewById(R.id.demendeStageEditText)
        val demendeEditText = demendeStage.editText*/
        //(demendeEditText as? AutoCompleteTextView)?.setAdapter(adapter)


       /* val itemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            Toast.makeText(requireContext(), "You selected $selectedItem", Toast.LENGTH_SHORT).show()
        }*/

       // (demendeEditText as? AutoCompleteTextView)?.setOnItemClickListener(itemClickListener)

        returnBtn.setOnClickListener {
            // Get the FragmentManager
            val fragmentManager = parentFragmentManager

            // Pop the current fragment and return to the previous one
            fragmentManager.popBackStack()
        }

    return view
    }



}