package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.core.view.isVisible
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.PlacemarkConfig
import expo.modules.yandexmap.view.YandexMapView.Companion.clusterConfig
import expo.modules.yandexmap.view.YandexMapView.Companion.clusterizedCollection
import expo.modules.yandexmap.view.YandexMapView.Companion.isMapLoaded
import expo.modules.yandexmap.view.YandexMapView.Companion.mapConfig
import expo.modules.yandexmap.view.YandexMapView.Companion.markersCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

@SuppressLint("ViewConstructor")
class MarkerView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
    val onMarkerPress by EventDispatcher()

    var placemark: PlacemarkMapObject? = null

    private var placemarkConfig: PlacemarkConfig = PlacemarkConfig()

    private var placemarkTapListener = MapObjectTapListener { _, point ->
        onMarkerPress(mapOf("latitude" to point.latitude, "longitude" to point.longitude))
        true
    }

    override fun onViewAdded(view: View?) {
        view?.addOnLayoutChangeListener { v, left, top, right, bottom, _, _, _, _ ->
            view.isVisible = false

            val bitmap = loadBitmapFromView(v, left, top, right, bottom)

            placemarkConfig.imageProvider = ImageProvider.fromBitmap(bitmap)

            if (isMapLoaded) {
                placemark?.setIcon(placemarkConfig.imageProvider!!, placemarkConfig.iconStyle)
            }
        }

        super.onViewAdded(view)
    }

    fun updateMarker() {
        if (mapConfig.clusterized) {
            updateClusterizedMarker()
        } else {
            updateUsualMarker()
        }
    }

    fun setCoordinate(latLng: Coordinate) {
        placemarkConfig.coordinate = latLng.toPoint()
    }

    fun setIconStyleValue(style: IconStyle) {
        placemarkConfig.iconStyle = style
    }

    private fun updateCommonMarker() {
        placemark?.geometry = placemarkConfig.coordinate
        placemark?.addTapListener(placemarkTapListener)

        if (placemarkConfig.imageProvider != null) {
            placemark?.setIcon(
                placemarkConfig.imageProvider!!,
                placemarkConfig.iconStyle
            )
        }
    }

    private fun updateClusterizedMarker() {
        placemark = clusterizedCollection?.addPlacemark()

        updateCommonMarker()

        clusterizedCollection?.clusterPlacemarks(clusterConfig.clusterRadius, clusterConfig.minZoom)
    }

    private fun updateUsualMarker() {
        placemark = markersCollection?.addPlacemark()

        updateCommonMarker()
    }

    private fun loadBitmapFromView(
        view: View,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): Bitmap {
        val width = if (right - left <= 0) 100 else right - left
        val height = if (bottom - top <= 0) 100 else bottom - top
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val c = Canvas(bitmap)

        view.draw(c)

        return bitmap
    }

    private suspend fun <T> loadFromUriSuspend(
        uri: String,
        processStream: (InputStream) -> T?
    ): T? =
        withContext(Dispatchers.IO) {
            try {
                val url = URL(uri)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val inputStream = connection.inputStream

                processStream(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}

