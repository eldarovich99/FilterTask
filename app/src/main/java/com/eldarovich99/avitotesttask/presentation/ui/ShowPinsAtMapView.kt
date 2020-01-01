package com.eldarovich99.avitotesttask.presentation.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.eldarovich99.avitotesttask.R
import com.eldarovich99.avitotesttask.domain.entity.Pin
import com.yandex.mapkit.MapKitFactory
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

    init {
        View.inflate(context, R.layout.map_view, this)

        imageProviderA = ImageProvider.fromResource(context, R.drawable.placemark_a)
        imageProviderB = ImageProvider.fromResource(context, R.drawable.placemark_b)
        imageProviderC = ImageProvider.fromResource(context, R.drawable.placemark_c)
        imageProviderUnknown = ImageProvider.fromResource(context, R.drawable.placemark_unknown)
    }

    /**
     * Displays pins on the map and moves the camera to the center of pins
     * @param pins are pins that should be displayed
     */
    fun showPins(pins: List<Pin>){
        if (pins.isNotEmpty()){
            var latitude = 0.0
            var longitude = 0.0
            for (pin in pins){
                latitude+=pin.coordinates.lat
                longitude+=pin.coordinates.lng
                showPoint(pin.toPoint(), pin.service)
            }
            val center = Point(latitude/pins.size, longitude/pins.size)
            yandexMap.map.move(CameraPosition(center, 12.0f, 0.0f, 0.0f))
        }
    }

    /**
     * Displays single point on the map
     * Uses placemarks with letters A, B, C for those types or a placemark without letter for other types
     * @param point is the point that should be displayed
     * @param type is service type
     */
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

    /**
     * Clears all pins and displays new pins
     * @param pins are pins that should be displayed
     */
    fun refreshPins(pins: List<Pin>){
        yandexMap.map.mapObjects.clear()
        showPins(pins)
    }

    override fun onAttachedToWindow() {
        yandexMap.onStart()
        MapKitFactory.getInstance().onStart()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        yandexMap.onStop()
        MapKitFactory.getInstance().onStop()
        super.onDetachedFromWindow()
    }
}