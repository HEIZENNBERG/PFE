package com.example.my_pfe.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_pfe.CustomAdapter
import com.example.my_pfe.NotificationAdapter
import com.example.my_pfe.R


class NotificationFragment : Fragment() {
    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var adapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder3>
    lateinit var notificationRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       /* notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        adapter = NotificationAdapter()
        notificationRecyclerView.adapter = adapter*/
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        layoutManager = LinearLayoutManager(activity)
        notificationRecyclerView.layoutManager = layoutManager
        // set the custom adapter to the RecyclerView
        adapter = NotificationAdapter()
        notificationRecyclerView.adapter = adapter
    }
}