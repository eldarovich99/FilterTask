package com.eldarovich99.avitotesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.eldarovich99.avitotesttask.R

class MapActivity : AppCompatActivity(), MapView {
    private val presenter by lazy { MapPresenter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.setData()
    }

    override fun showPoints() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showPoints(type: List<Int>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleErrors() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        presenter.onAttach(this)
        super.onStart()
    }

    override fun onStop() {
        presenter.onDetach()
        super.onStop()
    }
}
