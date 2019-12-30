package com.eldarovich99.avitotesttask.presentation.filter

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.di.scopes.ApplicationScope
import com.eldarovich99.avitotesttask.domain.entity.Service
import toothpick.ktp.KTP
import java.util.*
import javax.inject.Inject

class ServicesRecyclerViewAdapter(var items: ArrayList<Service>) : RecyclerView.Adapter<ServicesRecyclerViewAdapter.ServicesViewHolder?>() {
    @Inject
    lateinit var context: Application

    init {
        KTP.openScope(ApplicationScope::class.java).inject(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.service_item, parent, false)
        val holder = ServicesViewHolder(view)
       // holder.serviceCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
       //     items[holder.adapterPosition].isSelected = isChecked}
        holder.itemView.setOnClickListener {
            holder.serviceCheckBox.isChecked = !holder.serviceCheckBox.isChecked
            items[holder.adapterPosition].isSelected = holder.serviceCheckBox.isChecked
        }
        return holder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ServicesViewHolder, position: Int) {
        holder.serviceTextView.text = context.getString(R.string.service_template, items[position].title.toUpperCase(
            Locale.ROOT))
        holder.serviceCheckBox.isChecked = items[position].isSelected
    }

    class ServicesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val serviceTextView : TextView = itemView.findViewById(R.id.serviceTextView)
        val serviceCheckBox : AppCompatCheckBox = itemView.findViewById(R.id.checkBox)
    }
}