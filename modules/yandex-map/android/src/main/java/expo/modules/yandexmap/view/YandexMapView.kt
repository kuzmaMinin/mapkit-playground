package expo.modules.yandexmap.view

import CircleView
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.ScreenPoint
import com.yandex.mapkit.ScreenRect
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Geometry
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CircleMapObject
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapLoadedListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PolygonMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.model.ClusterConfig
import expo.modules.yandexmap.model.ClusterConfigModel
import expo.modules.yandexmap.model.ClusterStyleData
import expo.modules.yandexmap.model.DEFAULT_CLUSTER_AZIMUTH
import expo.modules.yandexmap.model.DEFAULT_CLUSTER_TILT
import expo.modules.yandexmap.model.INITIAL_CAMERA_ZOOM
import expo.modules.yandexmap.model.MapConfig
import expo.modules.yandexmap.model.Position

@SuppressLint("ViewConstructor")
class YandexMapView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
    val onMapReady by EventDispatcher()
    val onClusterPress by EventDispatcher()

    private var isMapLoaded = false

    private val markerViews = mutableListOf<MarkerView>()
    private val polygonViews = mutableListOf<PolygonView>()
    private val polylineViews = mutableListOf<PolylineView>()
    private val circleViews = mutableListOf<CircleView>()

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
                if (mapConfig.clusterized) {
                    clusterizedCollection?.remove(child.placemark as MapObject)

                    clusterizedCollection?.clusterPlacemarks(
                        clusterConfig.clusterRadius,
                        clusterConfig.minZoom
                    )
                } else {
                    mapObjects?.remove(child.placemark as MapObject)
                }
            }

            is PolygonView -> mapObjects?.remove(child.polygonMapObject as PolygonMapObject)
            is PolylineView -> mapObjects?.remove(child.polylineMapObject as PolylineMapObject)
            is CircleView -> mapObjects?.remove(child.circleMapObject as CircleMapObject)
        }
    }

    fun fitMarkers(points: List<Point>) {
        if (points.isEmpty()) {
            return
        }

        var cameraPosition =
            map?.cameraPosition(Geometry.fromBoundingBox(calculateBoundingBox(points)))

        if (cameraPosition != null) {
            cameraPosition = CameraPosition(
                cameraPosition.target,
                cameraPosition.zoom - 0.8f,
                cameraPosition.azimuth,
                cameraPosition.tilt
            )

            map?.move(cameraPosition, Animation(Animation.Type.SMOOTH, 0.7f), null)
        }
    }

    fun moveCamera(position: Position) {
        val point = position.toPoint()

        val zoom = position.zoom ?: map?.cameraPosition?.zoom
        val azimuth = position.azimuth ?: map?.cameraPosition?.azimuth
        val tilt = position.tilt ?: map?.cameraPosition?.tilt

        map?.move(
            CameraPosition(
                point,
                zoom ?: INITIAL_CAMERA_ZOOM,
                azimuth ?: DEFAULT_CLUSTER_AZIMUTH,
                tilt ?: DEFAULT_CLUSTER_TILT
            ),
            Animation(Animation.Type.LINEAR, 1f),
            cameraCallback
        )
    }

    fun setClusterizedValue(isClusterized: Boolean) {
        mapConfig.clusterized = isClusterized
    }

    fun setClusterStyleValue(style: ClusterStyleData) {
        mapConfig.clusterStyle = style
    }

    fun setClusterConfigValue(config: ClusterConfigModel) {
        clusterConfig = config.toClusterConfigData()
    }

    fun setInitialRegion(position: Position) {
        mapConfig.initialRegion = position
    }

    fun setScrollEnabled(isEnabled: Boolean) {
        map?.isScrollGesturesEnabled = isEnabled
    }

    fun setZoomEnabled(isEnabled: Boolean) {
        map?.isZoomGesturesEnabled = isEnabled
    }

    fun setRotationEnabled(isEnabled: Boolean) {
        map?.isRotateGesturesEnabled = isEnabled
    }

    fun setTiltEnabled(isEnabled: Boolean) {
        map?.isTiltGesturesEnabled = isEnabled
    }

    fun setFocusRect(tlX: Float, tlY: Float, brX: Float, brY: Float) {
        mapView?.mapWindow?.focusRect = ScreenRect(
            ScreenPoint(tlX, tlY),
            ScreenPoint(brX, brY)
        )
    }

    private val cameraCallback = Map.CameraCallback {
        // TODO: Handle camera move finished ...
    }

    private fun calculateBoundingBox(points: List<Point>): BoundingBox {
        var minLat = Double.MAX_VALUE
        var maxLat = -Double.MAX_VALUE
        var minLng = Double.MAX_VALUE
        var maxLng = -Double.MAX_VALUE

        points.forEach { point ->
            minLat = minOf(minLat, point.latitude)
            maxLat = maxOf(maxLat, point.latitude)
            minLng = minOf(minLng, point.longitude)
            maxLng = maxOf(maxLng, point.longitude)
        }

        return BoundingBox(
            Point(minLat, minLng),
            Point(maxLat, maxLng)
        )
    }

    private val clusterListener = ClusterListener { cluster ->
        cluster.appearance.setView(
            ViewProvider(
                ClusterView(context, appContext).apply {
                    setText("${cluster.placemarks.size}")
                    setPadding(mapConfig.clusterStyle.padding)
                    setFontSize(mapConfig.clusterStyle.fontSize)
                    setTextColor(mapConfig.clusterStyle.textColor)
                    setBackground(mapConfig.clusterStyle.backgroundColor)
                    setStroke(mapConfig.clusterStyle.strokeWidth, mapConfig.clusterStyle.strokeColor)
                }
            )
        )

        cluster.addClusterTapListener(clusterTapListener)
    }

    private val clusterTapListener = ClusterTapListener {
        onClusterPress(mapOf("size" to it.size))

        val markers =
            it.placemarks.map { point -> Point(point.geometry.latitude, point.geometry.longitude) }

        fitMarkers(markers)

        true
    }

    private val inputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            // TODO: Handle single tap ...
        }

        override fun onMapLongTap(map: Map, point: Point) {
            // TODO: Handle long tap ...
        }
    }

    // TODO: not implemented yet
    private val geoObjectTapListener = object : GeoObjectTapListener {
        override fun onObjectTap(event: GeoObjectTapEvent): Boolean {
            // Handle GeoObjectTapEvent ...
            return true
        }
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

        if (mapConfig.initialRegion != null) {
            map?.move(
                CameraPosition(
                    mapConfig.initialRegion!!.toPoint(),
                    mapConfig.initialRegion!!.zoom ?: INITIAL_CAMERA_ZOOM,
                    mapConfig.initialRegion!!.azimuth ?: DEFAULT_CLUSTER_AZIMUTH,
                    mapConfig.initialRegion!!.tilt ?: DEFAULT_CLUSTER_TILT
                ),
                Animation(Animation.Type.LINEAR, 1f),
                cameraCallback
            )
        }

        onMapReady(mapOf("payload" to "success"))
    }

    init {
        MapKitFactory.getInstance().onStart()

        mapView = MapView(context)
        mapView?.onStart()

        map = mapView?.mapWindow?.map
        mapObjects = map?.mapObjects

        clusterizedCollection = mapObjects?.addClusterizedPlacemarkCollection(clusterListener)

        addView(mapView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        map?.setMapLoadedListener(mapLoadedListener)
        map?.addInputListener(inputListener)
    }

    companion object {
        var mapView: MapView? = null

        var map: Map? = null

        var mapObjects: MapObjectCollection? = null

        var clusterizedCollection: ClusterizedPlacemarkCollection? = null

        var clusterConfig = ClusterConfig()

        var mapConfig = MapConfig()
    }
}
