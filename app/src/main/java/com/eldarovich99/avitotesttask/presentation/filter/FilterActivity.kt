package com.eldarovich99.avitotesttask.presentation.filter

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Service
import kotlinx.android.synthetic.main.activity_filter.*
import java.util.*

class FilterActivity : AppCompatActivity() {
    var services: ArrayList<Service>? = null

    companion object{
        const val SERVICES = "services"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        setToolbar()
        services = intent.getParcelableArrayListExtra<Service>(SERVICES)
        services?.let {
            servicesRecycler.adapter = ServicesRecyclerViewAdapter(it)
        }
    }

    private fun setToolbar(){
        toolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        intent.putParcelableArrayListExtra(SERVICES, services)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}
