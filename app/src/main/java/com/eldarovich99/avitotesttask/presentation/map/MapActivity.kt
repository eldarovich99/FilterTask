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

// TODO fix data resetting after screen rotation
class MapActivity : AppCompatActivity(), MapActivityView {
    @Inject
    lateinit var presenter : MapPresenter

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
        filterButton.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            presenter.attachServicesToIntent(intent)
            startActivityForResult(intent, FILTER_REQUEST)
        }
        presenter.onAttach(this)
        filterButton.isEnabled = savedInstanceState != null
        presenter.setData(savedInstanceState == null)
    }

    override fun showPins(pins: List<Pin>) {
        filterButton.isEnabled = true
        mapView.showPins(pins)
    }

    override fun handleErrors() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
       // presenter.onAttach(this)
        mapView.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    override fun refreshPins(pins: List<Pin>) {
        mapView.refreshPins(pins)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == FILTER_REQUEST && resultCode == Activity.RESULT_OK) {
            val newServices: ArrayList<Service>? = data?.getParcelableArrayListExtra(SERVICES)
            presenter.onAttach(this)
            presenter.refreshServicesAndPins(newServices!!)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
// TODO make the filter button unclickable while data is not loaded yet
// TODO replace different placemarks with one placemark, add clustering and onTapListener (optionally)