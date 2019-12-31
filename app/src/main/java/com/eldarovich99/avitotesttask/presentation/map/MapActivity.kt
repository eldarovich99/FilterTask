package com.eldarovich99.avitotesttask.presentation.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.di.modules.MapActivityModule
import com.eldarovich99.avitotesttask.di.scopes.ApplicationScope
import com.eldarovich99.avitotesttask.di.scopes.MapActivityScope
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service
import com.eldarovich99.avitotesttask.presentation.filter.FilterActivity
import com.eldarovich99.avitotesttask.presentation.ui.ShowPinsAtMapView
import com.yandex.mapkit.MapKitFactory
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.ktp.KTP
import javax.inject.Inject

class MapActivity : AppCompatActivity(), MapActivityView {
    @Inject
    lateinit var presenter : MapPresenter
    private var services : ArrayList<Service>?=null // must be moved to presenter to survive screen orientation changes

    companion object{
        const val FILTER_REQUEST = 1337
        const val SERVICES = "services"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.setApiKey(ShowPinsAtMapView.API_KEY)
        MapKitFactory.initialize(this)

        KTP.openScope(ApplicationScope::class.java).openSubScope(MapActivityScope::class.java).installModules(
            MapActivityModule(this)
        ).inject(this)

        setContentView(R.layout.activity_main)
        presenter.setData()
        filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            intent.putParcelableArrayListExtra(SERVICES, services)
            startActivityForResult(intent, FILTER_REQUEST)
        }
    }

    override fun setServices(services: ArrayList<Service>) {
        this.services = services
    }

    override fun showPins(pins: List<Pin>) {
        mapView.showPins(pins)
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

    override fun refreshPins(pins: List<Pin>) {
        mapView.refreshPins(pins)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILTER_REQUEST && resultCode == Activity.RESULT_OK) {
            val newServices: ArrayList<Service>? = data?.getParcelableArrayListExtra(SERVICES)
            if (newServices?.equals(services) != true){
                services = newServices
                presenter.onAttach(this)
                presenter.refreshPoints(services!!)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
// TODO make the filter button unclickable while data is not loaded yet
// TODO replace different placemarks with one placemark, add clustering and onTapListener (optionally)