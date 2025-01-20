package expo.modules.yandexmap.view

import CircleView
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CircleMapObject
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolygonMapObject
import com.yandex.mapkit.map.PolylineMapObject
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.yandexmap.model.ClusterConfig
import expo.modules.yandexmap.model.ClusterStyle

@SuppressLint("ViewConstructor")
class YandexMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  val onMapReady by EventDispatcher()
  val onClusterPress by EventDispatcher()

  private var isMapLoaded = false

  private val markerViews = mutableListOf<MarkerView>()
  private val polygonViews = mutableListOf<PolygonView>()
  private val polylineViews = mutableListOf<PolylineView>()
  private val circleViews = mutableListOf<CircleView>()

  private val clusterListener = ClusterListener { cluster ->
    cluster.appearance.setView(
      ViewProvider(
        ClusterView(context, appContext).apply {
          setText("${cluster.placemarks.size}")
          setPadding(clusterStyle.padding)
          setFontSize(clusterStyle.fontSize)
          setTextColor(clusterStyle.textColor)

          setBackground(clusterStyle.backgroundColor)
          setStroke(clusterStyle.strokeWidth, clusterStyle.strokeColor)
        }
      )
    )

    cluster.addClusterTapListener(clusterTapListener)
  }

  private val clusterTapListener = ClusterTapListener {
    onClusterPress(mapOf("size" to it.size))
    true
  }

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

    clusterizedCollection = mapObjects?.addClusterizedPlacemarkCollection(clusterListener)
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
      is MarkerView -> {
        if (clusterized) {
          clusterizedCollection?.remove(child.placemark as MapObject)

          clusterizedCollection?.clusterPlacemarks(clusterConfig.clusterRadius, clusterConfig.minZoom)
        } else {
          mapObjects?.remove(child.placemark as MapObject)
        }

      }
      is PolygonView -> mapObjects?.remove(child.polygonMapObject as PolygonMapObject)
      is PolylineView -> mapObjects?.remove(child.polylineMapObject as PolylineMapObject)
      is CircleView -> mapObjects?.remove(child.circleMapObject as CircleMapObject)
    }
  }

  fun setClusterizedValue(isClusterized: Boolean?) {
    if (isClusterized == true) {
      clusterized = true
    }
  }

  fun setClusterStyleValue(style: ClusterStyle) {
    clusterStyle.padding = style.padding
    clusterStyle.fontSize = style.fontSize
    clusterStyle.strokeColor = style.strokeColor
    clusterStyle.strokeWidth = style.strokeWidth
    clusterStyle.backgroundColor = style.backgroundColor
    clusterStyle.textColor = style.textColor
  }

  fun setClusterConfigValue(config: ClusterConfig) {
    clusterConfig.clusterRadius = config.clusterRadius
    clusterConfig.minZoom = config.minZoom
  }

  companion object {
    var mapView: MapView? = null

    var mapObjects: MapObjectCollection? = null

    var clusterized: Boolean = false

    var clusterStyle = ClusterStyle()

    var clusterConfig = ClusterConfig()

    var clusterizedCollection: ClusterizedPlacemarkCollection? = null
  }
}


