package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.PolylineMapObject
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.PolylineConfig
import expo.modules.yandexmap.utils.ColorUtils
import expo.modules.yandexmap.view.YandexMapView.Companion.mapObjects

@SuppressLint("ViewConstructor")
class PolylineView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  var polylineMapObject: PolylineMapObject? = null

  private var polylineConfig: PolylineConfig = PolylineConfig()

  fun setPointsValue(coordinates: List<Coordinate>) {
    polylineConfig.points = coordinates.map { point ->
      point.toPoint()
    }
  }

  fun setStrokeWidthValue(width: Double?) {
    if (width != null) {
      polylineConfig.strokeWidth = width.toFloat()
    }
  }

  fun setStrokeColorValue(color: String?) {
    if (color != null) {
      polylineConfig.strokeColor = ColorUtils.parseColor(color)
    }
  }

  fun setOutlineWidthValue(width: Double?) {
    if (width != null) {
      polylineConfig.outlineWidth = width.toFloat()
    }
  }

  fun setOutlineColorValue(color: String?) {
    if (color != null) {
      polylineConfig.outlineColor = ColorUtils.parseColor(color)
    }
  }

  fun updatePolyline() {
    if (polylineConfig.points != null) {
      val polyline = Polyline(polylineConfig.points!!)

      polylineMapObject = mapObjects?.addPolyline(polyline)?.apply {
        if (polylineConfig.strokeWidth != null) {
          strokeWidth = polylineConfig.strokeWidth!!
        }

        if (polylineConfig.strokeColor != null) {
          setStrokeColor(polylineConfig.strokeColor!!)
        }

        if (polylineConfig.outlineWidth != null) {
          outlineWidth = polylineConfig.outlineWidth!!
        }

        if (polylineConfig.outlineColor != null) {
          outlineColor = polylineConfig.outlineColor!!
        }
      }
    }
  }
}