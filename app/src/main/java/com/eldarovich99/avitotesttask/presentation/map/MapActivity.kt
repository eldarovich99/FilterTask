package com.eldarovich99.avitotesttask.presentation.map

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service
import com.eldarovich99.avitotesttask.presentation.filter.FilterActivity
import com.eldarovich99.avitotesttask.presentation.ui.ShowPinsAtMapView
import com.yandex.mapkit.MapKitFactory
import kotlinx.android.synthetic.main.activity_main.*

class MapActivity : AppCompatActivity(), MapActivityView {
    private val presenter by lazy {
        MapPresenter(
            this
        )
    }
    private var services : ArrayList<Service>?=null

    companion object{
        const val FILTER_INTENT = 1337
        const val SERVICES = "services"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(ShowPinsAtMapView.API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        presenter.setData()
        filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            intent.putParcelableArrayListExtra(SERVICES, services)
            startActivityForResult(intent, FILTER_INTENT)
        }
    }

    override fun setServiceNames(services: ArrayList<Service>) {
        this.services = services
    }

    override fun showPoints(points: List<Pin>) {
        mapView.showPins(points)
    }

    override fun handleErrors() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        presenter.onAttach(this)
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        presenter.onDetach()
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}
