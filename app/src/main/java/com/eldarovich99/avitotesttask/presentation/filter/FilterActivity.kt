package com.eldarovich99.avitotesttask.presentation.filter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Service
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity : AppCompatActivity() {
   // @Inject
   // lateinit var adapter: ServicesRecyclerViewAdapter
    companion object{
        const val SERVICES = "services"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val services = intent.getParcelableArrayListExtra<Service>(SERVICES)
        services?.let {
            servicesRecycler.adapter = ServicesRecyclerViewAdapter(services)
        }
    }
}
