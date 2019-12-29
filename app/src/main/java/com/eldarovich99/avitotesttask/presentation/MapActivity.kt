package com.eldarovich99.avitotesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.presentation.ui.ShowPinsAtMapView
import com.yandex.mapkit.MapKitFactory
import kotlinx.android.synthetic.main.activity_main.*

class MapActivity : AppCompatActivity(), MapActivityView {
    private val presenter by lazy { MapPresenter(this) }
    private var serviceNames : List<String>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(ShowPinsAtMapView.API_KEY)
        MapKitFactory.initialize(this)
        setContentView(R.layout.activity_main)
        presenter.setData()
    }

    override fun setServices(services: List<String>) {
        serviceNames = services
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
