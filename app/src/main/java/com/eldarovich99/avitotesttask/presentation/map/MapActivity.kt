package com.eldarovich99.avitotesttask.presentation.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.data.ErrorEntity
import com.eldarovich99.avitotesttask.di.modules.MapActivityModule
import com.eldarovich99.avitotesttask.di.scopes.ApplicationScope
import com.eldarovich99.avitotesttask.di.scopes.MapActivityScope
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.eldarovich99.avitotesttask.domain.entity.Service
import com.eldarovich99.avitotesttask.presentation.filter.FilterActivity
import com.eldarovich99.avitotesttask.presentation.ui.ShowPinsAtMapView
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKitFactory
import kotlinx.android.synthetic.main.activity_main.*
import toothpick.ktp.KTP
import javax.inject.Inject

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

    override fun handleErrors(errorEntity: ErrorEntity) {
        when (errorEntity){
            is ErrorEntity.AccessDenied -> Snackbar.make(mapView, getString(R.string.access_denied_message), Snackbar.LENGTH_SHORT).show()
            is ErrorEntity.InternalError -> Snackbar.make(mapView, getString(R.string.internal_error_message), Snackbar.LENGTH_SHORT).show()
            is ErrorEntity.Network -> Snackbar.make(mapView, getString(R.string.network_error_message), Snackbar.LENGTH_SHORT).show()
            is ErrorEntity.NotFound -> Snackbar.make(mapView, getString(R.string.not_found_message), Snackbar.LENGTH_SHORT).show()
            is ErrorEntity.ServiceUnavailable -> Snackbar.make(mapView, getString(R.string.service_unavailable), Snackbar.LENGTH_SHORT).show()
            is ErrorEntity.Unknown -> Snackbar.make(mapView, getString(R.string.unknown_error_message), Snackbar.LENGTH_SHORT).show()
        }
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