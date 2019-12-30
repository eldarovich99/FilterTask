package com.eldarovich99.avitotesttask.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import kotlinx.android.synthetic.main.map_view.view.*

class ShowPinsAtMapView(context: Context, attrSet : AttributeSet) : MapView(context, attrSet) {
    var imageProviderA : ImageProvider
    var imageProviderB : ImageProvider
    var imageProviderC : ImageProvider
    var imageProviderUnknown : ImageProvider
    companion object{
        const val API_KEY = "23739aa3-5d16-4949-8124-ff1d9c46f7c8"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int)  {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val width = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(height, width)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        //yandexMap.onStart()
        //MapKitFactory.getInstance().onStart()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
       // yandexMap.onStop()
       // MapKitFactory.getInstance().onStop()
    }

    init {
       // MapKitFactory.setApiKey(API_KEY)
       // MapKitFactory.initialize(context)
        View.inflate(context, R.layout.map_view, this)

        imageProviderA = ImageProvider.fromResource(context, R.drawable.placemark_a)
        imageProviderB = ImageProvider.fromResource(context, R.drawable.placemark_b)
        imageProviderC = ImageProvider.fromResource(context, R.drawable.placemark_c)
        imageProviderUnknown = ImageProvider.fromResource(context, R.drawable.ic_place_black_24dp)
    }

    fun showPins(pins: List<Pin>){
        if (pins.isNotEmpty()){
            yandexMap.map.move(CameraPosition(pins[0].toPoint(), 11.0f, 0.0f, 0.0f)) // TODO find center of placemarks
            for (pin in pins){
                showPoint(pin.toPoint(), pin.service)
            }
        }
    }

    fun showPoint(point: Point, type: String){
        yandexMap.map.mapObjects.let {
            when (type){
                "a" -> it.addPlacemark(point, imageProviderA)
                "b" -> it.addPlacemark(point, imageProviderB)
                "c" -> it.addPlacemark(point, imageProviderC)
                else -> it.addPlacemark(point, imageProviderUnknown)
            }
        }
    }

    fun refreshPoints(point: Point, types: List<String>){
        yandexMap.map.mapObjects.clear()
        for (type in types){
           // showPoint()
        }
    }
}