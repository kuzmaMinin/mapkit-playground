package expo.modules.yandexmap.view

import android.annotation.SuppressLint
import android.content.Context
import com.yandex.mapkit.geometry.LinearRing
import com.yandex.mapkit.geometry.Polygon
import com.yandex.mapkit.map.PolygonMapObject
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.views.ExpoView
import expo.modules.yandexmap.model.Coordinate
import expo.modules.yandexmap.model.PolygonConfig
import expo.modules.yandexmap.utils.ColorUtils
import expo.modules.yandexmap.view.YandexMapView.Companion.mapObjects

@SuppressLint("ViewConstructor")
class PolygonView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {
  var polygonMapObject: PolygonMapObject? = null

  private var polygonConfig: PolygonConfig = PolygonConfig()

  fun setPointsValue(coordinates: List<Coordinate>) {
    polygonConfig.points = coordinates.map { point ->
      point.toPoint()
    }
  }

  fun setInnerPointsValue(coordinates: List<Coordinate>?) {
    if (coordinates != null) {
      polygonConfig.innerPoints = coordinates.map { point ->
        point.toPoint()
      }
    }
  }

  fun setStrokeWidthValue(width: Double?) {
    if (width != null) {
      polygonConfig.strokeWidth = width.toFloat()
    }
  }

  fun setStrokeColorValue(color: String?) {
    if (color != null) {
      polygonConfig.strokeColor = ColorUtils.parseColor(color)
    }
  }

  fun setFillColorValue(color: String?) {
    if (color != null) {
      polygonConfig.fillColor = ColorUtils.parseColor(color)
    }
  }

  fun updatePolygon() {
    if (polygonConfig.points != null) {
      val exclude =
        if (polygonConfig.innerPoints == null) emptyList() else listOf(LinearRing(polygonConfig.innerPoints!!))

      val polygon = Polygon(LinearRing(polygonConfig.points!!), exclude)

      polygonMapObject = mapObjects?.addPolygon(polygon)?.apply {
        if (polygonConfig.fillColor != null) {
          fillColor = polygonConfig.fillColor!!
        }

        if (polygonConfig.strokeWidth != null) {
          strokeWidth = polygonConfig.strokeWidth!!
        }

        if (polygonConfig.strokeColor != null) {
          strokeColor = polygonConfig.strokeColor!!
        }
      }
    }
  }
}