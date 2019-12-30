package com.eldarovich99.avitotesttask.presentation.filter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Service
import java.util.*
import javax.inject.Inject

class ServicesRecyclerViewAdapter(var items: ArrayList<Service>) : RecyclerView.Adapter<ServicesRecyclerViewAdapter.ServicesViewHolder?>() {
    @Inject
    lateinit var context: Application

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
        return ServicesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.serviceTextView.text = items[position].title
        holder.serviceRadioButton.isChecked = items[position].isSelected
    }

    class ServicesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val serviceTextView : TextView = itemView.findViewById(R.id.serviceTextView)
        val serviceRadioButton : RadioButton = itemView.findViewById(R.id.radioButton)
    }
}