package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import com.yandex.mapkit.mapview.MapView

@SuppressLint("ViewConstructor")
class YandexMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  private var isMapLoaded = false
  private val markerViews = mutableListOf<MarkerView>()

  private val mapLoadedListener = MapLoadedListener {
    isMapLoaded = true

    for (markerView in markerViews) {
      markerView.updateMarker()
    }
  }

  init {
    MapKitFactory.getInstance().onStart()

    mapView = MapView(context)
    mapView?.onStart()
    mapObjects = mapView?.mapWindow?.map?.mapObjects

    addView(mapView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    mapView?.mapWindow?.map?.setMapLoadedListener(mapLoadedListener)
  }

  override fun onViewAdded(child: View?) {
    super.onViewAdded(child)

    if (!isMapLoaded && child is MarkerView) {
      markerViews.add(child)

      return
    }

    if (child is MarkerView) {
      child.addOnLayoutChangeListener { v, left, top, right, bottom, _, _, _, _ ->
        child.isVisible = false
        child.updateMarker()
      }
    } else {
      // TODO: handle throwing error if !is MarkerView
      // throw IllegalArgumentException("Child view must be a MarkerView")
    }
  }

  override fun onViewRemoved(child: View?) {
    // TODO: handle removing marker
    super.onViewRemoved(child)

    if (child is MarkerView) {
      mapObjects?.remove(child.placemark as MapObject)
    }
  }

  companion object {
    var mapView: MapView? = null

    var mapObjects: MapObjectCollection? = null
  }
}


