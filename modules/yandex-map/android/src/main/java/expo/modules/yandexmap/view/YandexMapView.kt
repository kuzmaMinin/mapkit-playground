package expo.modules.yandexmap.view

import CircleView
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CircleMapObject
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolygonMapObject
import com.yandex.mapkit.map.PolylineMapObject
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import com.yandex.mapkit.mapview.MapView
import expo.modules.kotlin.viewevent.EventDispatcher

@SuppressLint("ViewConstructor")
class YandexMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  val onMapReady by EventDispatcher()

  private var isMapLoaded = false

  private val markerViews = mutableListOf<MarkerView>()
  private val polygonViews = mutableListOf<PolygonView>()
  private val polylineViews = mutableListOf<PolylineView>()
  private val circleViews = mutableListOf<CircleView>()

  private val mapLoadedListener = MapLoadedListener {
    isMapLoaded = true

    for (markerView in markerViews) {
      markerView.updateMarker()
    }

    for (polygonView in polygonViews) {
      polygonView.updatePolygon()
    }

    for (polylineView in polylineViews) {
      polylineView.updatePolyline()
    }

    for (circleView in circleViews) {
      circleView.updateCircle()
    }

    onMapReady(mapOf("payload" to "success"))
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

    when {
      !isMapLoaded && child is MarkerView -> markerViews.add(child)
      !isMapLoaded && child is PolygonView -> polygonViews.add(child)
      !isMapLoaded && child is PolylineView -> polylineViews.add(child)
      !isMapLoaded && child is CircleView -> circleViews.add(child)
      isMapLoaded && child is MarkerView -> {
        child.isVisible = false
        child.updateMarker()
      }
      isMapLoaded && child is PolygonView -> child.updatePolygon()
      isMapLoaded && child is PolylineView -> child.updatePolyline()
      isMapLoaded && child is CircleView -> child.updateCircle()
    }
  }

  override fun onViewRemoved(child: View?) {
    super.onViewRemoved(child)

    when (child) {
      is MarkerView -> mapObjects?.remove(child.placemark as MapObject)
      is PolygonView -> mapObjects?.remove(child.polygonMapObject as PolygonMapObject)
      is PolylineView -> mapObjects?.remove(child.polylineMapObject as PolylineMapObject)
      is CircleView -> mapObjects?.remove(child.circleMapObject as CircleMapObject)
    }
  }

  companion object {
    var mapView: MapView? = null

    var mapObjects: MapObjectCollection? = null
  }
}


